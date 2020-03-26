export class FirebaseUserModel {

    name: string;
    provider: string;
    isAdmin: boolean;
    golfCourse: string;

    constructor() {
      this.name = '';
      this.provider = '';
      this.isAdmin = true;
      this.golfCourse = '';
    }

}
