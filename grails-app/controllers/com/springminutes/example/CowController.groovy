package com.springminutes.example

import grails.converters.XML
import grails.converters.JSON

class CowController {

    static allowedMethods = [save: "POST", update: ["POST", "PUT"], delete: ["POST", "DELETE"]]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        withFormat {
            html { [cowInstanceList: Cow.list(params), cowInstanceTotal: Cow.count()] }
            xml { render Cow.list(params) as XML }
            json { render Cow.list(params) as JSON }
        }
    }

    def create = {
        def cowInstance = new Cow()
        cowInstance.properties = params
        return [cowInstance: cowInstance]
    }

    def save = {
        def cowInstance = new Cow(params)
        if (cowInstance.save(flush: true)) {
            withFormat {
                form {
                    flash.message = "${message(code: 'default.created.message', args: [message(code: 'cow.label', default: 'Cow'), cowInstance.id])}"
                    redirect(action: "show", id: cowInstance.id)
                }
                xml {
                    response.status = 200 // OK
                    render cowInstance as XML
                }
                json {
                    response.status = 200 // OK
                    render cowInstance as JSON
                }
            }
        }
        else {
            withFormat {
                form {
                    render(view: "create", model: [cowInstance: cowInstance])
                }
                xml {
                    response.status = 400 // Bad Request
                    render cowInstance.errors as XML
                }
                json {
                    response.status = 400 // Bad Request
                    render cowInstance.errors as JSON
                }
            }
        }
    }

    def renderNotFound = {
        html {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
            redirect(action: "list")
        }
        form {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
            redirect(action: "list")
        }
        xml {
            response.status = 404
            render "Cow not found."
        }
        json  {
            response.status = 404
            render "Cow not found."
        }
    }

    def show = {
        def cowInstance = Cow.get(params.id)
        if (!cowInstance) {
            withFormat renderNotFound
        }
        else {
            withFormat {
                html { [cowInstance: cowInstance] }
                xml { render cowInstance as XML }
                json { render cowInstance as JSON }
            }
        }
    }

    def edit = {
        def cowInstance = Cow.get(params.id)
        if (!cowInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
            redirect(action: "list")
        }
        else {
            [cowInstance: cowInstance]
        }
    }

    def update = {
        def cowInstance = Cow.get(params.id)
        if (cowInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (cowInstance.version > version) {
                    
                    cowInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'cow.label', default: 'Cow')] as Object[], "Another user has updated this Cow while you were editing")
                    render(view: "edit", model: [cowInstance: cowInstance])
                    return
                }
            }
            cowInstance.properties = params
            if (!cowInstance.hasErrors() && cowInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'cow.label', default: 'Cow'), cowInstance.id])}"
                redirect(action: "show", id: cowInstance.id)
            }
            else {
                render(view: "edit", model: [cowInstance: cowInstance])
            }
        }
        else {
            withFormat renderNotFound
        }
    }

    def delete = {
        def cowInstance = Cow.get(params.id)
        if (cowInstance) {
            try {
                cowInstance.delete(flush: true)
                withFormat {
                    form {
                        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
                        redirect(action: "list")
                    }
                    xml {
                        response.status = 200 // OK
                        render "${message(code: 'default.deleted.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
                    }
                    json {
                        response.status = 200 // OK
                        render "${message(code: 'default.deleted.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
                    }
                }
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                withFormat {
                    form {
                        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
                        redirect(action: "show", id: params.id)
                    }
                    xml {
                        response.status = 409 // Conflict
                        render "${message(code: 'default.not.deleted.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
                    }
                    json {
                        response.status = 409 // Conflict
                        render "${message(code: 'default.not.deleted.message', args: [message(code: 'cow.label', default: 'Cow'), params.id])}"
                    }
                }
            }
        }
        else {
            withFormat renderNotFound
        }
    }
}
