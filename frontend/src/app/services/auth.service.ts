import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  getId(): string {
    let user = sessionStorage.getItem("id");
    return user ? user : '';
  }

  setId(id: string) {
    sessionStorage.setItem('id', id);
  }

  isAuthenticated() {
    return sessionStorage.getItem("id") ? true : false;
  }

  logout() {
    sessionStorage.removeItem('id');
    sessionStorage.removeItem('token');
  }
  getToken(): string {
    return sessionStorage.getItem('token') ? sessionStorage.getItem('token') : '';
  }

  setToken(token: string) {
    sessionStorage.setItem('token', token.split(' ')[1]);
  }

}
