import {bootstrap, Component, View, Control, CORE_DIRECTIVES, FORM_DIRECTIVES} from 'angular2/angular2';
import {Http, Headers, HTTP_BINDINGS} from 'angular2/http';

@Component({
  selector: 'app',
  template: `
  {{diagnostic}}
  <table class="table table-hover">
    <thead>
      <tr>
        <th>#</th>
        <th>Username</th>
        <th>Privileges</th>
      </tr>
    </thead>
    <tbody>
			<tr *ng-for="#user of users">
				<td>{{user.id}}</td>
        <td>{{user.username}}</td>
        <td>
          <select [(ng-model)]="user.privilege" [ng-form-control]="selectControl" (change)="update(user, selectControl.value)">
            <option *ng-for="#p of privileges" [value]="p">{{p}}</option>
          </select>
        </td>
			</tr>
    </tbody>
  </table>
  `,
  directives: [CORE_DIRECTIVES, FORM_DIRECTIVES]
})

export class AppComponent {
  users;
  privileges = ['Admin', 'User'];
  selectControl: Control = new Control('');

  constructor(public http: Http) {
    http.get('/WebShop/api/user')
      .subscribe(res => {
        this.users = res.json();
        this.users.forEach(function(user) {
          user.privilege = 'User';
          if(user.categoryRead && user.categoryWrite && user.categoryDelete &&
            user.itemRead && user.itemWrite && user.itemDelete &&
            user.itemCommentRead && user.itemCommentWrite && user.itemCommentDelete) {
              user.privilege = 'Admin';
          }
        })
      });
  }

  update(user, value) {
    var bitmap = 100100110;
    if(value === 'Admin') {
      bitmap = 111111111;
    }
    var parameters = "userId=" + user.id + "&privileges=" + bitmap;
    alert(parameters);
    var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');

    this.http.put('/WebShop/api/user', parameters, {
      headers: headers
    });
  }

  get diagnostic() { return JSON.stringify(this.users); }
}

bootstrap(AppComponent, [HTTP_BINDINGS]);
