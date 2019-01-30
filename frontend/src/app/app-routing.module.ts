import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { PostImageComponent } from './post-image/post-image.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './guard/auth.guard'

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'profile/:profileId', component: ProfileComponent, canActivate: [AuthGuard]},
  { path: 'postImage', component: PostImageComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: '/login' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
