import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  url: string = 'http://localhost:8080/';
  constructor(private http: HttpClient ) { }

  login(username: string, password: string) {
    console.log(username, password);
    return this.http.post(`${this.url}login`, {
      username: username,
      password: password
    }, { observe: 'response', responseType: 'text' });
  }

  register(username: string, email: string, password: string) {
    console.log(email, password, username);
    return this.http.post(`${this.url}users/auth/sign-up`, {
      email: email,
      password: password,
      username: username
    }, { observe: 'response' });
  }

  getProfileData(id) {
    return this.http.get(`${this.url}users/profile/${id}`, { observe: 'response' });
  }
  getFollowers(id) {
    return this.http.get(`${this.url}users/followers/${id}`, { observe: 'response' });
  }
  getFollowing(id) {
    return this.http.get(`${this.url}users/following/${id}`, { observe: 'response' });
  }
  getPosts(id) {
    return this.http.get(`${this.url}users/posts/${id}`, { observe: 'response' });
  }
  changeEmail(email: string) {
    let user = JSON.parse(sessionStorage.getItem("user"));

    return this.http.post(`${this.url}users/updateEmail/${user.id}`,
      email
      , { observe: 'response' });
  }
  changePassword(password: string) {
    let user = JSON.parse(sessionStorage.getItem("user"));

    return this.http.post(`${this.url}users/updatePassword/${user.id}`,
      password
      , { observe: 'response' });
  }

  getAvatar(id: string) {
    return this.http.get(`${this.url}users/getAvatar/${id}`, { observe: 'response', responseType: 'blob' });
  }

  changeAvatar(id, file: File) {
    const fd = new FormData();
    fd.append('file', file, file.name);
    return this.http.post(`${this.url}users/updateAvatar/${id}`, fd, { observe: 'response' });
  }
  postImage(id, file: File) {
    const fd = new FormData();
    fd.append('file', file, file.name);
    console.log(id, 'id');
    return this.http.post(`${this.url}users/postImage/${id}`, fd, { observe: 'response', responseType: 'text' });
  }

  isFollowing(userId, followingId) {
    return this.http.post(`${this.url}users/isFollowing`,
      {
        followerId: followingId,
        userId: userId}
      , { observe: 'response' });
  }

  unFollow(userId, followingId, route) {
    return this.http.post(`${this.url}users/${route}`,
      {
        followingId: followingId,
        userId: userId
      }
      , { observe: 'response' });
  }

  getMessages(sender, receiver) {
    return this.http.post(`${this.url}message/sentMessage`,
      {
        senderId: sender,
        receiverId: receiver
      }
      , { observe: 'response' });
  }

  sendMessage(sender, receiver, message) {
    return this.http.post(`${this.url}message`,
      {
        senderId: sender,
        receiverId: receiver,
        message: message
      }
      , { observe: 'response' });
  }
  getAllPosts(limit, offset) {
    return this.http.get(`${this.url}users/myPosts/${limit}/${offset}`, { observe: 'response' });
  }
  getImage(url) {
    return this.http.get(`${this.url}users/getImage/${url}`, { observe: 'response', responseType: 'blob' });
  }

  countPosts() {
    return this.http.get(`${this.url}users/posts/count`, { observe: 'response' });
  }

  like(postId, userId) {
    return this.http.post(`${this.url}users/like`,
      {
        postId: postId,
        userId: userId
      }
      , { observe: 'response' });
  }

  getUserValidated(username: string) {
    return this.http.get(`${this.url}users/auth/${username}`, {observe: 'response'});
  }

}
