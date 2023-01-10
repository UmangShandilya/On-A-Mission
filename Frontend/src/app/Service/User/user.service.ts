import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService 
{

  // Constructor 
  constructor(private client:HttpClient) { }

  // Flag
  isAuthenticated : boolean = false;
 
  //URL
  URL = "http://localhost:8080/";

  // Function for registering a new user
  signUpProcess(credential : any)
  {
    return this.client.post(this.URL  + "todo-app" + "/" + "register",credential);
  }

  // Function for authenticating a user
  signInProcess(credential : any)
  {
    return this.client.post(this.URL + "auth" + "/" + "login", credential);
  }
}
