import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = sessionStorage.getItem('token');
    if (token) {

      const headers = request.headers
        .set('Authorization', 'Basic ${token}')
        .set('Content-Type', 'application/json');

      request = request.clone({ headers });
    }


    return next.handle(request);
  }
}