import { Component, OnInit } from '@angular/core';
import { BackendService } from '../services/backend.service';
import { HttpResponse } from '@angular/common/http';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-post-image',
  templateUrl: './post-image.component.html',
  styleUrls: ['./post-image.component.css']
})
export class PostImageComponent implements OnInit {

  selectedFile: File = null;
  user = null;
  constructor(private backend: BackendService, private auth: AuthService) { }

  ngOnInit() {
    this.user = JSON.parse(sessionStorage.getItem("user"));
  }
  fileSelected(event) {
    console.log(event);
    this.selectedFile = event.target.files[0];
    this.backend.postImage(this.auth.getId(), this.selectedFile).subscribe((response: HttpResponse<Object>) => {
      console.log(response);
    });
  }
}
