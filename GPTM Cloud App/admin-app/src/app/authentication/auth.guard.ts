import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';
import {Router} from '@angular/router';
import { AngularFireAuth } from '@angular/fire/auth';
import { UserService } from '../services/user.service';
@Injectable()
export class AuthGuard implements CanActivate {

  constructor(
    public afAuth: AngularFireAuth,
    public userService: UserService,
    private router: Router
  ) {}

  // Allows access to the app if authenticated
  canActivate(): Promise<boolean> {
    return new Promise ((resolve) => {
      this.userService.getCurrentUser()
      .then(() => {
        this.router.navigate(['/dashboard']);
        return resolve(false);
      }, () => {
        return resolve(true);
      });
    });
  }
}
