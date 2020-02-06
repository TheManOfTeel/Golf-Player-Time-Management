export class FirebaseUserModel {

    image: string;
    name: string;
    provider: string;
    isAdmin: boolean;
    golfCourse: string;

    constructor() {
      this.image = '';
      this.name = '';
      this.provider = '';
      this.isAdmin = true;
      this.golfCourse = '';
    }

}
