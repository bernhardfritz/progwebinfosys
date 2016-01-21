// main.jsx
/* jshint ignore:start */
var NavbarFormLoggedOut = React.createClass({
  getInitialState: function() {
    return {username: '', password: ''};
  },
  handleUsernameChange: function(e) {
    this.setState({username: e.target.value});
  },
  handlePasswordChange: function(e) {
    this.setState({password: e.target.value});
  },
  handleSubmit: function() {
    e.preventDefault();
    var username = this.state.username.trim();
    var password = this.state.password.trim();
    if(!username || !password) {
      return;
    }
    $.post('/FA/api/user/login', {username: username, password: password});
    this.setState({username: '', password: ''});
  },
  render: function() {
    return(
      <form className='navbar-form navbar-right' onSubmit={this.handleSubmit}>
        <div className='form-group'>
          <input
            type='text'
            className='form-control'
            placeholder='Username'
            value={this.state.username}
            onChange={this.handleUsernameChange}
          />
        </div>
        &nbsp;
        <div className='form-group'>
          <input
            type='password'
            className='form-control'
            placeholder='Password'
            value={this.state.password}
            onChange={this.handlePasswordChange}
          />
        </div>
        &nbsp;
        <input
          type='submit'
          className='btn btn-success'
          value='Sign in'
        />
        &nbsp;
        <a id='signUp' className='btn btn-primary' href='#' data-toggle='modal' data-target='#signUpModal'>Sign up</a>
      </form>
    );
  }
});

var NavbarFormLoggedIn = React.createClass({
  render: function() {
    return(
      <form className='navbar-form navbar-right'>
        <text id='helloText' className='text-muted'>Hello, {this.props.username}!</text>
        &nbsp;
        <input
          type='submit'
          className='btn btn-danger'
          value='Logout'
        />
      </form>
    );
  }
});

var Navbar = React.createClass({
  getInitialState: function() {
    return {username: ''};
  },
  getCurrentUser: function() {
    $.get(this.props.url, function(data) {
      console.log(data);
      var username='asdf';
      this.setState({username: username});
    });
  },
  componentDidMount: function() {
    this.getCurrentUser();
    setInterval(this.getCurrentUser, this.props.pollInterval);
  },
  render: function() {
    var navbarForm;
    if (this.state.username) {
      navbarForm = <NavbarFormLoggedIn username={this.state.username} />
    } else {
      navbarForm = <NavbarFormLoggedOut />
    }
    return(
      <nav className="navbar navbar-inverse navbar-fixed-top">
        <div className="container">
          <div className="navbar-header">
            <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span className="sr-only">Toggle navigation</span>
              <span className="icon-bar"></span>
              <span className="icon-bar"></span>
              <span className="icon-bar"></span>
            </button>
            <a className="navbar-brand" href="/">Financial Administration</a>
          </div>
          <div id="navbar" className="navbar-collapse collapse">
            {navbarForm}
          </div>
        </div>
      </nav>
    );
  }
});

var Account = React.createClass({
  render: function() {
    return(
      <div className="account">
        <form>
          <div className="form-group">
            <select>
              <option>asdf</option>
            </select>
          </div>
        </form>
        <table className="table table-condensed">
          <thead>
            <tr>
              <th>AccountId</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
});

var Component = React.createClass({
  render: function() {
    return(
      <div className="component">
        <Navbar url='/FA/api/user/current' pollInterval={2000} />
        <Account />
      </div>
    );
  }
});

ReactDOM.render(
  <Component />,
  document.getElementById('content')
);
/* jshint ignore:end */
