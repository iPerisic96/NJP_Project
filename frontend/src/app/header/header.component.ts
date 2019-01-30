import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  searchForm: FormGroup;

  ngOnInit(): void {
    if (!this.auth.isAuthenticated) {
      this.router.navigate(['/login']);
    }
    this.createForm();
  }



  navbarOpen = false;
  constructor(private auth: AuthService, private formBuilder: FormBuilder, private router: Router) { }


  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }
  createForm() {
    this.searchForm = this.formBuilder.group({
      'searchValue': [null]
    })
  }
  logout() {
    this.auth.logout();
  }
}
