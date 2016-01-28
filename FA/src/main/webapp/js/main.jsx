// main.jsx
/* jshint ignore:start */

var NavbarFormLoggedOut = React.createClass({
  getInitialState: function() {
    return {username: '', password: ''};
  },
  componentDidMount: function() {
    $('#usernameField').focus();
  },
  handleUsernameChange: function(e) {
    this.setState({username: e.target.value});
  },
  handlePasswordChange: function(e) {
    this.setState({password: e.target.value});
  },
  handleSubmit: function(e) {
    e.preventDefault();
    var username = this.state.username.trim();
    var password = this.state.password.trim();
    if(!username || !password) {
      return;
    }
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/user/login',
      data: JSON.stringify({username: username, password: password}),
      complete: function(data) {
        this.props.parent.setState({username: username, password: password});
      }.bind(this),
      dataType: 'json'
    });
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
            id='usernameField'
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
        <button type='submit' className='btn btn-success' id="signInButton">Sign in</button>
        &nbsp;
        <button className='btn btn-primary' data-toggle='modal' data-target='#signUpModal'>Sign up</button>
      </form>
    );
  }
});

var NavbarFormLoggedIn = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    $.post('/FA/api/user/logout', {}, function(data) {
      this.props.parent.setState({username: '', password: ''});
    }.bind(this));
  },
  render: function() {
    return(
      <form className='navbar-form navbar-right' onSubmit={this.handleSubmit}>
        <text id='helloText' className='text-muted'>Hello, {this.props.parent.state.username}!</text>
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
    return {username: '', signUpUsername: '', signUpPassword: ''};
  },
  getCurrentUser: function() {
    $.get(this.props.url, function(data) {
      console.log(data);
      var username = data.username;
      this.setState({username: username});
    }.bind(this));
  },
  componentDidMount: function() {
    this.getCurrentUser();
  },
  handleSignUpUsernameChange: function(e) {
    this.setState({signUpUsername: e.target.value});
  },
  handleSignUpPasswordChange: function(e) {
    this.setState({signUpPassword: e.target.value});
  },
  handleSignUpSubmit: function(e) {
    e.preventDefault();
    var username = this.state.signUpUsername.trim();
    var password = this.state.signUpPassword.trim();
    if(!username || !password) {
      return;
    }
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/user',
      data: JSON.stringify({username: username, password: password}),
      success: function(data) {
        console.log(data);
        $('#signUpCloseButton').click();
        this.setState({signUpUsername: '', signUpPassword: ''});
        this.setState({username: username, password: password});
        $('#retypePasswordSignup').val('');
        $('#signInButton').click();
      }.bind(this),
      dataType: 'json'
    });
  },
  render: function() {
    var navbarForm;
    if (this.state.username) {
      navbarForm = <NavbarFormLoggedIn parent={this} />
    } else {
      navbarForm = <NavbarFormLoggedOut parent={this} />
    }
    return(
      <div className='navbar-wrapper'>
        <nav className="navbar navbar-inverse navbar-fixed-top">
          <div className="container">
            <div className="navbar-header">
              <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span className="sr-only">Toggle navigation</span>
                <span className="icon-bar"></span>
                <span className="icon-bar"></span>
                <span className="icon-bar"></span>
              </button>
              <a className="navbar-brand" href="/FA/">Financial Administration</a>
            </div>
            <div id="navbar" className="navbar-collapse collapse">
              {navbarForm}
            </div>
          </div>
        </nav>
        <form role="form" data-toggle="validator" onSubmit={this.handleSignUpSubmit}>
          <div className="modal fade" role="dialog" id="signUpModal">
            <div className="modal-dialog">
              <div className="modal-content">
                <div className="modal-header">
                  <button type="button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 className="modal-title">Sign up</h4>
                </div>
                <div className="modal-body">
                    <div className="form-group">
                      <label className="control-label">Username:</label>
                      <input
                        type="text"
                        className="form-control"
                        value={this.state.signUpUsername}
                        onChange={this.handleSignUpUsernameChange}
                        autofocus
                        required />
                    </div>
                    <div className="form-group">
                      <label className="control-label">Password:</label>
                      <input
                        type="password"
                        className="form-control"
                        id="passwordSignup"
                        data-minlength="6"
                        value={this.state.signUpPassword}
                        onChange={this.handleSignUpPasswordChange}
                        required />
                      <span className="help-block">Minimum of 6 characters</span>
                    </div>
                    <div className="form-group">
                      <label className="control-label">Retype password:</label>
                      <input type="password" id="retypePasswordSignup" className="form-control" data-match="#passwordSignup" data-match-error="Whoops, these don't match" required />
                      <div className="help-block with-errors"></div>
                    </div>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-default" data-dismiss="modal" id="signUpCloseButton">Close</button>
                  <button type="submit" className="btn btn-primary">Register</button>
                </div>
              </div>
            </div>
          </div>
        </form>
        <Accounts parent={this} />
      </div>
    );
  }
});

var Account = React.createClass({
  getInitialState: function() {
    return {account: {}, operations: []};
  },
  getAccount: function() {
    if(this.props.accountNumber) {
      $.get('/FA/api/account/'+this.props.accountNumber, function(data) {
        this.setState({account: data});
      }.bind(this));
    } else {
      this.setState({account: {}});
    }
  },
  getOperations: function() {
    if(this.props.accountNumber) {
      $.get('/FA/api/operation/all/'+this.props.accountNumber, function(data) {
        this.setState({operations: data});
      }.bind(this));
    } else {
      this.setState({operations: []});
    }
  },
  componentDidMount: function() {
    this.getAccount();
    this.getOperations();
    this.props.parent.state.intervalIds.push(setInterval(this.getAccount, 500));
    this.props.parent.state.intervalIds.push(setInterval(this.getOperations, 500));
  },
  componentWillReceiveProps: function(nextProps) {
    this.getAccount();
    this.getOperations();
  },
  render: function() {
    var rows = [];
    this.state.operations.forEach(function(operation) {
      if (operation.fromAccount.accountNumber == operation.toAccount.accountNumber) {
        if (operation.amount < 0) {
          rows.push(<tr className="danger"><td>{moment(operation.createTimestamp).format('DD.MM.YYYY HH:mm')}</td><td>{operation.fromAccount.accountNumber}</td><td>{operation.amount.toFixed(2)}</td></tr>);
        } else {
          rows.push(<tr className="success"><td>{moment(operation.createTimestamp).format('DD.MM.YYYY HH:mm')}</td><td>{operation.toAccount.accountNumber}</td><td>+{operation.amount.toFixed(2)}</td></tr>);
        }
      } else {
      	if(operation.fromAccount.accountNumber === this.props.accountNumber) {
          rows.push(<tr className="danger"><td>{moment(operation.createTimestamp).format('DD.MM.YYYY HH:mm')}</td><td>{operation.toAccount.accountNumber}</td><td>-{operation.amount.toFixed(2)}</td></tr>);
        } else {
          rows.push(<tr className="success"><td>{moment(operation.createTimestamp).format('DD.MM.YYYY HH:mm')}</td><td>{operation.fromAccount.accountNumber}</td><td>+{operation.amount.toFixed(2)}</td></tr>);
        }
      }
    }.bind(this));
    var balance;
    if(this.state.account.balance || this.state.account.balance === 0) {
      if(this.state.account.balance >= 0) {
        balance = <td><strong><text className="text-success">+{this.state.account.balance.toFixed(2)}</text></strong></td>
      } else {
        balance = <td><strong><text className="text-danger">{this.state.account.balance.toFixed(2)}</text></strong></td>
      }
    }
    if(this.props.accountNumber) {
      return(
        <div className="account">
          <table className="table table-condensed">
            <thead>
              <tr>
                <th>Date</th>
                <th>Account</th>
                <th>Amount</th>
              </tr>
            </thead>
            <tbody>
              {rows}
            </tbody>
            <tfoot>
              <tr>
                <td></td>
                <td className="alnright"><strong>=</strong></td>
                {balance}
              </tr>
            </tfoot>
          </table>
        </div>
      );
    } else {
      return(
        <div className="account"></div>
      )
    }
  }
});

var Menu = React.createClass({
  getInitialState: function() {
    return {
      transferTo: '',
      transferAmount: '',
      depositAmount: '',
      withdrawAmount: ''
    };
  },
  handleTransferSubmit: function(e) {
    e.preventDefault();
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/operation/transfer',
      data: JSON.stringify({fromAccountNumber: this.props.accountNumber, toAccountNumber: this.state.transferTo, amount: this.state.transferAmount}),
      success: function(data) {
        console.log(data);
        $("#transferCloseButton").click();
        this.setState({transferTo: '', transferAmount: ''});
      }.bind(this),
      dataType: 'json'
    });
  },
  handleTransferToChange: function(e) {
    this.setState({transferTo: e.target.value});
  },
  handleTransferAmountChange: function(e) {
    this.setState({transferAmount: e.target.value});
  },
  handleDepositSubmit: function(e) {
    e.preventDefault();
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/operation/deposit',
      data: JSON.stringify({accountNumber: this.props.accountNumber, amount: this.state.depositAmount}),
      success: function(data) {
        console.log(data);
        $("#depositCloseButton").click();
        this.setState({depositAmount: ''});
      }.bind(this),
      dataType: 'json'
    });
  },
  handleDepositAmountChange: function(e) {
    this.setState({depositAmount: e.target.value});
  },
  handleWithdrawSubmit: function(e) {
    e.preventDefault();
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/operation/withdraw',
      data: JSON.stringify({accountNumber: this.props.accountNumber, amount: this.state.withdrawAmount}),
      success: function(data) {
        console.log(data);
        $("#withdrawCloseButton").click();
        this.setState({withdrawAmount: ''});
      }.bind(this),
      dataType: 'json'
    });
  },
  handleWithdrawAmountChange: function(e) {
    this.setState({withdrawAmount: e.target.value});
  },
  render: function() {
    if(this.props.accountNumber) {
      return(
        <div className="menu">
          <button className="btn btn-default" data-toggle="modal" data-target="#transferModal">Transfer</button>&nbsp;
          <button className="btn btn-default" data-toggle="modal" data-target="#depositModal">Deposit</button>&nbsp;
          <button className="btn btn-default" data-toggle="modal" data-target="#withdrawModal">Withdraw</button>
          <form onSubmit={this.handleTransferSubmit}>
          <div className="modal fade" role="dialog" id="transferModal">
            <div className="modal-dialog">
              <div className="modal-content">
                <div className="modal-header">
                  <button type="button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 className="modal-title">Transfer</h4>
                </div>
                <div className="modal-body">
                  <div className='form-group'>
                    <label>Transfer to:</label>
                    <input
                      type="text"
                      className="form-control"
                      value={this.state.transferTo}
                      onChange={this.handleTransferToChange}
                    />
                  </div>
                  <div className='form-group'>
                    <label>Transfer amount:</label>
                    <input
                      type="text"
                      className="form-control"
                      value={this.state.transferAmount}
                      onChange={this.handleTransferAmountChange}
                    />
                  </div>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-default" data-dismiss="modal" id="transferCloseButton">Close</button>
                  <input
                    type="submit"
                    className="btn btn-primary"
                    value="Transfer"
                  />
                </div>
              </div>
            </div>
          </div>
          </form>
          <form onSubmit={this.handleDepositSubmit}>
          <div className="modal fade" role="dialog" id="depositModal">
            <div className="modal-dialog">
              <div className="modal-content">
                <div className="modal-header">
                  <button type="button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 className="modal-title">Deposit</h4>
                </div>
                <div className="modal-body">
                  <div className='form-group'>
                    <label>Deposit amount:</label>
                    <input
                      type="text"
                      className="form-control"
                      value={this.state.depositAmount}
                      onChange={this.handleDepositAmountChange}
                    />
                  </div>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-default" data-dismiss="modal" id="depositCloseButton">Close</button>
                  <input
                    type="submit"
                    className="btn btn-primary"
                    value="Deposit"
                  />
                </div>
              </div>
            </div>
          </div>
          </form>
          <form onSubmit={this.handleWithdrawSubmit}>
          <div className="modal fade" role="dialog" id="withdrawModal">
            <div className="modal-dialog">
              <div className="modal-content">
                <div className="modal-header">
                  <button type="button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 className="modal-title">Withdraw</h4>
                </div>
                <div className="modal-body">
                  <div className='form-group'>
                    <label>Withdraw amount:</label>
                    <input
                      type="text"
                      className="form-control"
                      value={this.state.withdrawAmount}
                      onChange={this.handleWithdrawAmountChange}
                    />
                  </div>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-default" data-dismiss="modal" id="withdrawCloseButton">Close</button>
                  <input
                    type="submit"
                    className="btn btn-primary"
                    value='Withdraw'
                  />
                </div>
              </div>
            </div>
          </div>
          </form>
        </div>
      );
    } else {
      return(
        <div className="menu"></div>
      );
    }
  }
});

var ChildAccounts = React.createClass({
    render: function(){
      return (
        <div className="panel panel-default">
         <div className="panel-heading" role="tab" id="headingOne">
           <h4 className="panel-title">
             <a role="button" id={this.props.account.accountNumber} onClick={this.props.parent.handleThatEvent.bind(this, this.props.account.accountNumber)} data-toggle="collapse" data-parent="#accordion" href={"#collapse"+this.props.account.accountNumber} aria-expanded="false" aria-controls={"collapse" + this.props.account.accountNumber}>
               {this.props.account.accountNumber}
             </a>
           </h4>
         </div>
         <div id={"collapse" + this.props.account.accountNumber} className="panel-collapse collapse" role="tabpanel" aria-labelledby={"heading"+this.props.account.accountNumber}>
           <div className="panel-body">
             <Account parent={this.props.parent} accountNumber={this.props.parent.state.selectedAccountNumber} />
           </div>
         </div>
        </div>
      );
    }
});

var Accounts = React.createClass({
  getInitialState: function() {
    return {accounts: [], selectedAccountNumber: '', intervalIds: []};
  },
  getAccounts: function() {
    $.get('/FA/api/account/all/'+this.props.parent.state.username, function(data) {
      this.setState({accounts: data});
    }.bind(this));
  },
  componentWillReceiveProps: function(nextProps) {
    if(this.props.parent.state.username) {
      this.getAccounts();
    } else {
      this.setState({accounts: [], selectedAccountNumber: ''});
      this.state.intervalIds.forEach(function(intervalId) {
          clearInterval(intervalId);
      });
    }
  },
  handleThatEvent: function(accountNumber, e){
    this.setState({selectedAccountNumber: accountNumber});
  },
  render: function() {
    var collapses = [];
    this.state.accounts.forEach(function(account) {
      collapses.push(
        <ChildAccounts account={account} parent={this} />
      );
    }.bind(this));
    return(
      <div className="accounts">
        <div className="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
          {collapses}
        </div>
        <Menu accountNumber={this.state.selectedAccountNumber} />
      </div>
    );
  }
});

var Footer = React.createClass({
  render: function() {
      return(
        <div className="footer">
      		<hr />
      		<div className="footer-content">
      			<p>Copyright &copy; Bernhard FRITZ & Mario ZELGER 2016</p>
      		</div>
      	</div>
      );
  }
});

var Component = React.createClass({
  render: function() {
    return(
      <div className="component">
        <Navbar url='/FA/api/user/current' />
        <Footer />
      </div>
    );
  }
});

ReactDOM.render(
  <Component />,
  document.getElementById('content')
);
/* jshint ignore:end */
