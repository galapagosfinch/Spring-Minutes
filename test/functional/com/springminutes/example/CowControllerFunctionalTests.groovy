package com.springminutes.example

/**
 * Functional test for RESTful methods of CowController
 */
class CowControllerFunctionalTests extends functionaltestplugin.FunctionalTestCase {
    void testGetList() {
        (new Cow(breed: Breed.Guernsey, color: 'yellow', legs: 3)).save()
        (new Cow(breed: Breed.Holstein, color: 'blue', legs: 4)).save()
        assertEquals 2, Cow.count()

        get('http://localhost:8080/moo/rest/cow/list') {
            headers['Content-Type'] = 'application/json'
        }
        assertStatus 200

        assertContentContains "Guernsey"
        assertContentContains "Holstein"
    }

    void testGetItem() {
        def originalCount = Cow.count()
        assertTrue originalCount >= 2 // should contain the entries from testGetList

        assertEquals Breed.Guernsey, Cow.get(1).breed
        get('http://localhost:8080/moo/rest/cow/element/1.json')
        assertStatus 200
        assertContentContains "Guernsey"

        assertEquals Breed.Holstein, Cow.get(2).breed
        get('http://localhost:8080/moo/rest/cow/element/2.json')
        assertStatus 200
        assertContentContains "Holstein"
    }

    void testInsert() {
        def originalCount = Cow.count()

        post('http://localhost:8080/moo/rest/cow/list') {
            headers['Accept'] = 'application/json'
            body {"""
            {"breed": "Guernsey", "color":"blue", "legs":"1"}
            """
            }
        }
        assertStatus 200
        assertEquals originalCount + 1, Cow.count()
        assertContentContains('"legs":1')
        assertContentContains('"color":"blue"')
    }

    void testUpdate() {
        def originalCount = Cow.count()
        def cow1 = Cow.get(1)
        assertEquals Breed.Guernsey, cow1.breed
        assertEquals "yellow", cow1.color

        put('http://localhost:8080/moo/rest/cow/element/1') {
            headers['Accept'] = 'application/json'
            body {"""
            {"breed": "TexasLonghorn", "color":"teal", "legs":"1"}
            """
            }
        }
        assertStatus 200
        assertEquals originalCount, Cow.count()
        cow1.refresh() // reload from database; row changed because of call to put()
        assertEquals Breed.TexasLonghorn, cow1.breed
        assertEquals "teal", cow1.color
    }

    void testDelete() {
            def originalCount = Cow.count()
            assertTrue Cow.exists(1)
            delete('http://localhost:8080/moo/rest/cow/element/1')
            assertStatus 200
            assertContentContains "Cow 1 deleted"
            assertEquals originalCount - 1, Cow.count()
            assertFalse Cow.exists(1)
    }
}
