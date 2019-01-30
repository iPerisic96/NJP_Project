import { Component, OnInit } from '@angular/core';
import { BackendService } from '../services/backend.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  updateForm: FormGroup;
  profileId = '';
  followers = 0;
  user: any;
  following = 0;
  posts = 0;
  imageToShow: any;
  isImageLoading = true;
  isFollowing = false;
  username = '';
  constructor(private auth: AuthService, private formBuilder: FormBuilder, private backend: BackendService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.profileId = this.route.snapshot.paramMap.get("profileId");
    this.backend.getProfileData(this.profileId).subscribe((response: HttpResponse<Object>) => {
      console.log(response);
      this.followers = response.body['followers'];
      this.following = response.body['following'];
      this.posts = response.body['posts'];
      this.username = response.body['username'];
    });

    // this.isImageLoading = true;
    // this.backend.getAvatar(this.profileId).subscribe((response: HttpResponse<Object>) => {
    //   console.log(response);
    //   this.createImageFromBlob(<Blob>response.body);
    //   this.isImageLoading = false;

    // });

    this.backend.isFollowing(this.auth.getId(), this.profileId).subscribe((response: HttpResponse<Object>) => {
      console.log(response);
      this.isFollowing = <boolean>response.body;
    });
  }



  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  checkUser() {
    if (this.profileId == this.auth.getId()) {
      return true;
    } else {
      return false;
    }
  }

  followOrUnfollow() {
    if (this.isFollowing) {
      return 'Unfollow';
    } else {
      return 'Follow';
    }
  }

  followOrUnfollowSmall() {
    if (this.isFollowing) {
      return 'unfollow';
    } else {
      return 'follow';
    }
  }

  unFollow() {
    this.backend.unFollow(this.auth.getId(), this.profileId, this.followOrUnfollowSmall()).subscribe((response: HttpResponse<Object>) => {
      console.log(response);
      this.backend.isFollowing(this.auth.getId(), this.profileId).subscribe((response: HttpResponse<Object>) => {
        console.log(response);
        this.isFollowing = <boolean>response.body;
      });
    });
  }
}
