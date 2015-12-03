import {bootstrap, Component, View, Control, CORE_DIRECTIVES, FORM_DIRECTIVES} from 'angular2/angular2';
import {Http, Headers, HTTP_BINDINGS} from 'angular2/http';

@Component({
  selector: 'app',
  template: `
  <!--{{diagnostic}}-->
  <table class="table table-hover">
    <thead>
      <tr>
        <th>#</th>
        <th>Username</th>
        <th>Privileges</th>
        <th>Delete</th>
      </tr>
    </thead>
    <tbody>
			<tr *ng-for="#user of users">
				<td>{{user.id}}</td>
        <td>{{user.username}}</td>
        <td>
          <select *ng-if="currentUser.username==='superadmin'" [(ng-model)]="user.privilege" [ng-form-control]="selectControl" (change)="update(user, selectControl.value)">
            <option *ng-for="#p of privileges" [value]="p">{{p}}</option>
          </select>
        </td>
        <td><button *ng-if="currentUser.username==='admin' || currentUser.username==='superadmin'" class='btn btn-sm btn-danger' (click)="delete(user)"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
			</tr>
    </tbody>
  </table>
  `,
  directives: [CORE_DIRECTIVES, FORM_DIRECTIVES]
})

export class AppComponent {
  currentUser;
  users;
  privileges = ['guest', 'user', 'admin', 'superadmin'];
  selectControl: Control = new Control('');

  constructor(public http: Http) {
    http.get('/WebShop/api/user/current')
      .subscribe(res => {
        this.currentUser = res.json();
      });
    http.get('/WebShop/api/user')
      .subscribe(res => {
        this.users = res.json();
        this.users.forEach(function(user) {
          user.privilege = 'guest';

          if(user.categoryRead && user.itemRead && user.itemCommentRead && user.itemCommentWrite) {
              user.privilege = 'user';
          }

          if(user.categoryRead && user.categoryWrite && user.categoryDelete &&
            user.itemRead && user.itemWrite && user.itemDelete &&
            user.itemCommentRead && user.itemCommentWrite && user.itemCommentDelete &&
            user.userDelete) {
              user.privilege = 'admin';
          }

          if(user.categoryRead && user.categoryWrite && user.categoryDelete &&
            user.itemRead && user.itemWrite && user.itemDelete &&
            user.itemCommentRead && user.itemCommentWrite && user.itemCommentDelete &&
            user.userPromote && user.userDemote && user.userDelete) {
              user.privilege = 'superadmin';
          }
        });
        this.users = this.users.filter(function(user) {
          return user.privilege !== 'guest' && user.privilege !== 'superadmin';
        });
        this.privileges = ['user', 'admin'];
      });
  }

  update(user, value) {
    var bitmap = 100100100000;

    if(value === 'user') {
      bitmap = 100100110000;
    } else if(value === 'admin') {
      bitmap = 111111111001;
    } else if(value === 'superadmin') {
      bitmap = 111111111111;
    }

    var parameters = "privileges=" + bitmap;
    var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');

    this.http.put('/WebShop/api/user/' + user.id, parameters, {headers: headers}).subscribe(res => console.log(res));
  }

  delete(user) {
    this.http.delete('/WebShop/api/user/' + user.id).subscribe(res => console.log(res));
    var index = this.users.indexOf(user);
    if(index  > -1) {
      this.users.splice(index, 1);
    }
  }

  get diagnostic() { return JSON.stringify(this.currentUser) + JSON.stringify(this.users); }
}

bootstrap(AppComponent, [HTTP_BINDINGS]);
