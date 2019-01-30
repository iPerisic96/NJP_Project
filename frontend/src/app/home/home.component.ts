import { Component, OnInit } from '@angular/core';
import { BackendService } from '../services/backend.service';
import { HttpResponse } from '@angular/common/http';
import { count } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  posts = [];
  images = [];
  count = 0;
  currentPage = 0;
  constructor(private backend: BackendService, private auth: AuthService, private router: Router) { }

  ngOnInit() {
    this.backend.countPosts().subscribe((response: HttpResponse<Object>) => {
      this.count = <number>response.body;
      console.log(this.count);
    });
  }

  getImage(url) {
    console.log('url', url);
    this.backend.getImage(url).subscribe((response: HttpResponse<Object>) => {
      this.createImageFromBlob(<Blob>response.body);

    });
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.images.push(reader.result);
      console.log(this.images);
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  paginate(event) {
    //event.first = Index of the first record
    //event.rows = Number of rows to display in new page
    //event.page = Index of the new page
    //event.pageCount = Total number of pages
    console.log(event.first, event.rows, event.page)
    this.currentPage = event.first;
    console.log("SSS");
    this.backend.getAllPosts(event.rows, event.first).subscribe((response: HttpResponse<Object>) => {
      this.posts = <any[]>response.body;
      console.log(response);
      for (let j = 0; this.posts.length; j++) {
        let string = /[^\\]*$/.exec(this.posts[j][0])[0]; //getovanje fajl imena posle poslednjeg slesha
        this.getImage(string.substring(0, string.indexOf('.'))); //brisanje ekstenzije iz fajl imena
        console.log(this.images);
      }
      
    });
  }

  getImgForId(id) {
    console.log('IDDD', id);
    for (let ind = 0; ind < this.posts.length; ind++) {
      if (this.posts[ind][2] === id) {
        return this.images[ind];
      }
    }
  }

  like(){
    console.log("Like");
  }


}
