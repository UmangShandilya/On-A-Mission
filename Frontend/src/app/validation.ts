import { AbstractControl } from "@angular/forms";

export function passwordMatch(password:string,confirmPassword:string){
     return function(form:AbstractControl){
        const passwvalue=form.get(password)?.value
        const confirmPasswvalue=form.get(confirmPassword)?.value

        if(passwvalue===confirmPasswvalue){
            return null
        }
        return {passwordMisMatchError:true}
    }
}