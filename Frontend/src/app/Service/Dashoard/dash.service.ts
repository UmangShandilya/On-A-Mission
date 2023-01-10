import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Notification } from 'src/app/Model/Notification';
import { UserTask } from 'src/app/Model/Task';

@Injectable({
  providedIn: 'root'
})
export class DashService {

  constructor(private client: HttpClient, private snackBar: MatSnackBar) { }

  // Storing authenticated email
  authenticatedEmail: any;

  // URL to send requests via HTTP Client
  URL = "http://localhost:8080/";

  // METHODS RELATED TO TASK

  // Method to fetch user's name.
  getUsername() {
    return this.client.get(this.URL + "todo-app/" + "user-name/" + this.authenticatedEmail, { responseType: 'text' });
  }

  updateUserName(userName : any)
  {
    return this.client.put(this.URL + "todo-app/" + "user-name/" + "update/" + this.authenticatedEmail  , userName)
  }
  // Method to fetch tasks of a user
  fetchTask() {
    return this.client.get<UserTask[]>(this.URL + "todo-app/" + "fetch-task/" + this.authenticatedEmail);
  }

  // Method to add a new task
  addTask(formContent: any) {
    return this.client.post(this.URL + "todo-app/" + "todo-list/" + this.authenticatedEmail, formContent);
  }

  // Method to edit details of an existing task
  editTask(taskID: any, task: any) {
    return this.client.put(this.URL + "todo-app/" +  "update-task/" + this.authenticatedEmail + "/" + taskID, task);
  }

  // Method to send request to delete a task
  deleteTask(taskID: number) {
    return this.client.delete(this.URL + "todo-app/" + "delete/" + this.authenticatedEmail + "/" + taskID);
  }

  // Method to get all tasks of a user
  fetchCompletedTask() {
    return this.client.get<UserTask[]>(this.URL + "todo-app/" + "task-completed/" + this.authenticatedEmail);
  }

  // Method to get all unfinished tasks of a user
  fetchUnfinishedTask() {
    return this.client.get<UserTask[]>(this.URL + "todo-app/" + "task-unfinished/" + this.authenticatedEmail);
  }

  // Method to update task details
  updateTaskStatusDetails(taskDetails: UserTask) {
    return this.client.put(this.URL + "todo-app/" + "completed/" + this.authenticatedEmail + "/" + taskDetails.taskID, taskDetails);
  }

  // Method to get all tasks of a user
  fetchPriorityTask(choosePriority: string) {
    return this.client.get<UserTask[]>(this.URL + "todo-app/" + "priority/" + this.authenticatedEmail + "/" + choosePriority);
  }


  // METHODS RELATED TO NOTIFICATION

  // Method to get all notification of a user
  fetchNotification() {
    return this.client.get<Notification[]>(this.URL + "notification/" + "fetch/" + this.authenticatedEmail);
  }

  // Method to delete a notification of a user
  deleteNotification(notificationID: any) {
    return this.client.delete(this.URL + "notification/" + "delete/" + this.authenticatedEmail + "/" + notificationID);
  }
  
}
