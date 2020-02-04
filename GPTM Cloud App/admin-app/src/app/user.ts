export interface Roles {
    basic: boolean;
    admin?:  boolean;
  }

export class User {
    email:    string;
    roles:    Roles;

  constructor(authData) {
    this.email    = authData.email
    this.roles    = { basic: false, admin: true }
  }
}
