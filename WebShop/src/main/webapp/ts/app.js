var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var angular2_1 = require('angular2/angular2');
var http_1 = require('angular2/http');
var AppComponent = (function () {
    function AppComponent(http) {
        var _this = this;
        this.http = http;
        this.privileges = ['guest', 'user', 'admin', 'superadmin'];
        this.selectControl = new angular2_1.Control('');
        http.get('/WebShop/api/user/current')
            .subscribe(function (res) {
            _this.currentUser = res.json();
        });
        http.get('/WebShop/api/user')
            .subscribe(function (res) {
            _this.users = res.json();
            _this.users.forEach(function (user) {
                user.privilege = 'guest';
                if (user.categoryRead && user.itemRead && user.itemCommentRead && user.itemCommentWrite) {
                    user.privilege = 'user';
                }
                if (user.categoryRead && user.categoryWrite && user.categoryDelete &&
                    user.itemRead && user.itemWrite && user.itemDelete &&
                    user.itemCommentRead && user.itemCommentWrite && user.itemCommentDelete &&
                    user.userDelete) {
                    user.privilege = 'admin';
                }
                if (user.categoryRead && user.categoryWrite && user.categoryDelete &&
                    user.itemRead && user.itemWrite && user.itemDelete &&
                    user.itemCommentRead && user.itemCommentWrite && user.itemCommentDelete &&
                    user.userPromote && user.userDemote && user.userDelete) {
                    user.privilege = 'superadmin';
                }
            });
            _this.users = _this.users.filter(function (user) {
                return user.privilege !== 'guest' && user.privilege !== 'superadmin';
            });
            _this.privileges = ['user', 'admin'];
        });
    }
    AppComponent.prototype.update = function (user, value) {
        var bitmap = 100100100000;
        if (value === 'user') {
            bitmap = 100100110000;
        }
        else if (value === 'admin') {
            bitmap = 111111111001;
        }
        else if (value === 'superadmin') {
            bitmap = 111111111111;
        }
        var parameters = "privileges=" + bitmap;
        var headers = new http_1.Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
        this.http.put('/WebShop/api/user/' + user.id, parameters, { headers: headers }).subscribe(function (res) { return console.log(res); });
    };
    AppComponent.prototype.delete = function (user) {
        this.http.delete('/WebShop/api/user/' + user.id).subscribe(function (res) { return console.log(res); });
        var index = this.users.indexOf(user);
        if (index > -1) {
            this.users.splice(index, 1);
        }
    };
    Object.defineProperty(AppComponent.prototype, "diagnostic", {
        get: function () { return JSON.stringify(this.currentUser) + JSON.stringify(this.users); },
        enumerable: true,
        configurable: true
    });
    AppComponent = __decorate([
        angular2_1.Component({
            selector: 'app',
            template: "\n  <!--{{diagnostic}}-->\n  <table id=\"userTable\" class=\"table table-hover\">\n    <thead>\n      <tr>\n        <th>#</th>\n        <th>Username</th>\n        <th>Privileges</th>\n        <th>Delete</th>\n      </tr>\n    </thead>\n    <tbody>\n\t\t\t<tr *ng-for=\"#user of users\">\n\t\t\t\t<td>{{user.id}}</td>\n        <td>{{user.username}}</td>\n        <td>\n          <select *ng-if=\"currentUser.username==='superadmin'\" [(ng-model)]=\"user.privilege\" [ng-form-control]=\"selectControl\" (change)=\"update(user, selectControl.value)\">\n            <option *ng-for=\"#p of privileges\" [value]=\"p\">{{p}}</option>\n          </select>\n        </td>\n        <td><button *ng-if=\"currentUser.username==='admin' || currentUser.username==='superadmin'\" class='btn btn-sm btn-danger' (click)=\"delete(user)\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button></td>\n\t\t\t</tr>\n    </tbody>\n  </table>\n  ",
            directives: [angular2_1.CORE_DIRECTIVES, angular2_1.FORM_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], AppComponent);
    return AppComponent;
})();
exports.AppComponent = AppComponent;
angular2_1.bootstrap(AppComponent, [http_1.HTTP_BINDINGS]);
//# sourceMappingURL=app.js.map