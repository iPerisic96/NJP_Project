import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BackendService } from '../services/backend.service';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registrationForm: FormGroup;
  submitted: boolean;
  constructor(private formBuilder: FormBuilder, private backend: BackendService, private router: Router) { }

  ngOnInit() {
    this.createForm();
    this.submitted = false;
  }

  createForm() {
    this.registrationForm = this.formBuilder.group({
      'email': ['', [Validators.required, Validators.email]],
      'username': ['', Validators.required],
      'password': ['', [Validators.required, Validators.minLength(6)]]
    })
  }

  // convenience getter for easy access to form fields
  get f() { return this.registrationForm.controls; }


  register() {
    this.submitted = true;

        // stop here if form is invalid
        if (this.registrationForm.invalid) {
            return;
        }
    this.backend.register(this.registrationForm.controls['username'].value, this.registrationForm.controls['email'].value, this.registrationForm.controls['password'].value).subscribe((response: HttpResponse<Object>) => {
      // this.auth.setToken(response.headers.get('Authorization'));
      // this.router.navigate(['/']);
      console.log(response);
      if (response.body == null){
        alert("Username is already taken");
      }
    });
  }
  
}
