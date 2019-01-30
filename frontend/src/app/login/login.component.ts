import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BackendService } from '../services/backend.service';
import { HttpResponse, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted = false;
  res: string;
  ress: string[];
  validated = false;
  constructor(private formBuilder: FormBuilder, private backend: BackendService, private router: Router, private auth: AuthService) { }

  ngOnInit() {
    this.createForm();

  }

  createForm() {
    this.loginForm = this.formBuilder.group({
      'username': ['', Validators.required],
      'password': ['', [Validators.required, Validators.minLength(6)]]
    })
  }

  // convenience getter for easy access to form fields
  get f() { return this.loginForm.controls; }


  login() {
    this.submitted = true;
    if (this.loginForm.invalid){
      return;
    }

    this.backend.getUserValidated(this.loginForm.controls['username'].value).subscribe((response: HttpResponse<Object>) => {
      console.log(response);
      if (response) {
        this.validated = <boolean>response.body;
        console.log(this.validated);
      }
    })

    this.backend.login(this.loginForm.controls['username'].value, this.loginForm.controls['password'].value).subscribe((response: HttpResponse<Object>) => {
      console.log(response.status);
      if (response.status === 200 && this.validated) {
        this.auth.setToken(response.headers.get('Authorization'));
        this.res = <string>response.body;
        console.log(this.res);
        this.auth.setId(this.res); //username
        this.router.navigate(['/']);
      }
    }, (error: any) => {
      if (error.status === 403){
        alert("Bad Credentials!");
      }
    })
  }

}
