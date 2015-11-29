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
        this.privileges = ['Admin', 'User'];
        this.selectControl = new angular2_1.Control('');
        http.get('/WebShop/api/user')
            .subscribe(function (res) {
            _this.users = res.json();
            _this.users.forEach(function (user) {
                user.privilege = 'User';
                user.style = '';
                if (user.categoryRead && user.categoryWrite && user.categoryDelete &&
                    user.itemRead && user.itemWrite && user.itemDelete &&
                    user.itemCommentRead && user.itemCommentWrite && user.itemCommentDelete &&
                    !user.userPromote && !user.userDemote && user.userDelete) {
                    user.privilege = 'Admin';
                }
                if (user.categoryRead && user.categoryWrite && user.categoryDelete &&
                    user.itemRead && user.itemWrite && user.itemDelete &&
                    user.itemCommentRead && user.itemCommentWrite && user.itemCommentDelete &&
                    user.userPromote && user.userDemote && user.userDelete) {
                    user.style = 'display: none';
                }
                else if (user.categoryRead && !user.categoryWrite && !user.categoryDelete &&
                    user.itemRead && !user.itemWrite && !user.itemDelete &&
                    user.itemCommentRead && !user.itemCommentWrite && !user.itemCommentDelete &&
                    !user.userPromote && !user.userDemote && !user.userDelete) {
                    user.style = 'display: none';
                }
            });
        });
    }
    AppComponent.prototype.update = function (user, value) {
        var bitmap = 100100110000;
        if (value === 'Admin') {
            bitmap = 111111111001;
        }
        var parameters = "userId=" + user.id + "&privileges=" + bitmap;
        alert(parameters);
        var headers = new http_1.Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
        this.http.put('/WebShop/api/user', parameters, {
            headers: headers
        });
    };
    Object.defineProperty(AppComponent.prototype, "diagnostic", {
        get: function () { return JSON.stringify(this.users); },
        enumerable: true,
        configurable: true
    });
    AppComponent = __decorate([
        angular2_1.Component({
            selector: 'app',
            template: "\n  {{diagnostic}}\n  <table class=\"table table-hover\">\n    <thead>\n      <tr>\n        <th>#</th>\n        <th>Username</th>\n        <th>Privileges</th>\n      </tr>\n    </thead>\n    <tbody>\n\t\t\t<tr *ng-for=\"#user of users\" style=\"{{user.style}}\">\n\t\t\t\t<td>{{user.id}}</td>\n        <td>{{user.username}}</td>\n        <td>\n          <select [(ng-model)]=\"user.privilege\" [ng-form-control]=\"selectControl\" (change)=\"update(user, selectControl.value)\">\n            <option *ng-for=\"#p of privileges\" [value]=\"p\">{{p}}</option>\n          </select>\n        </td>\n\t\t\t</tr>\n    </tbody>\n  </table>\n  ",
            directives: [angular2_1.CORE_DIRECTIVES, angular2_1.FORM_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [http_1.Http])
    ], AppComponent);
    return AppComponent;
})();
exports.AppComponent = AppComponent;
angular2_1.bootstrap(AppComponent, [http_1.HTTP_BINDINGS]);
//# sourceMappingURL=app.js.map