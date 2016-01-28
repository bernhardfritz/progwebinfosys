(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
// main.jsx
/* jshint ignore:start */

var NavbarFormLoggedOut = React.createClass({
  displayName: 'NavbarFormLoggedOut',

  getInitialState: function () {
    return { username: '', password: '' };
  },
  componentDidMount: function () {
    $('#usernameField').focus();
  },
  handleUsernameChange: function (e) {
    this.setState({ username: e.target.value });
  },
  handlePasswordChange: function (e) {
    this.setState({ password: e.target.value });
  },
  handleSubmit: function (e) {
    e.preventDefault();
    var username = this.state.username.trim();
    var password = this.state.password.trim();
    if (!username || !password) {
      return;
    }
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/user/login',
      data: JSON.stringify({ username: username, password: password }),
      complete: function (data) {
        this.props.parent.setState({ username: username, password: password });
      }.bind(this),
      dataType: 'json'
    });
    this.setState({ username: '', password: '' });
  },
  render: function () {
    return React.createElement(
      'form',
      { className: 'navbar-form navbar-right', onSubmit: this.handleSubmit },
      React.createElement(
        'div',
        { className: 'form-group' },
        React.createElement('input', {
          type: 'text',
          className: 'form-control',
          placeholder: 'Username',
          value: this.state.username,
          onChange: this.handleUsernameChange,
          id: 'usernameField'
        })
      ),
      ' ',
      React.createElement(
        'div',
        { className: 'form-group' },
        React.createElement('input', {
          type: 'password',
          className: 'form-control',
          placeholder: 'Password',
          value: this.state.password,
          onChange: this.handlePasswordChange
        })
      ),
      ' ',
      React.createElement(
        'button',
        { type: 'submit', className: 'btn btn-success', id: 'signInButton' },
        'Sign in'
      ),
      ' ',
      React.createElement(
        'button',
        { className: 'btn btn-primary', 'data-toggle': 'modal', 'data-target': '#signUpModal' },
        'Sign up'
      )
    );
  }
});

var NavbarFormLoggedIn = React.createClass({
  displayName: 'NavbarFormLoggedIn',

  handleSubmit: function (e) {
    e.preventDefault();
    $.post('/FA/api/user/logout', {}, function (data) {
      this.props.parent.setState({ username: '', password: '' });
    }.bind(this));
  },
  render: function () {
    return React.createElement(
      'form',
      { className: 'navbar-form navbar-right', onSubmit: this.handleSubmit },
      React.createElement(
        'text',
        { id: 'helloText', className: 'text-muted' },
        'Hello, ',
        this.props.parent.state.username,
        '!'
      ),
      ' ',
      React.createElement('input', {
        type: 'submit',
        className: 'btn btn-danger',
        value: 'Logout'
      })
    );
  }
});

var Navbar = React.createClass({
  displayName: 'Navbar',

  getInitialState: function () {
    return { username: '', signUpUsername: '', signUpPassword: '' };
  },
  getCurrentUser: function () {
    $.get(this.props.url, function (data) {
      console.log(data);
      var username = data.username;
      this.setState({ username: username });
    }.bind(this));
  },
  componentDidMount: function () {
    this.getCurrentUser();
  },
  handleSignUpUsernameChange: function (e) {
    this.setState({ signUpUsername: e.target.value });
  },
  handleSignUpPasswordChange: function (e) {
    this.setState({ signUpPassword: e.target.value });
  },
  handleSignUpSubmit: function (e) {
    e.preventDefault();
    var username = this.state.signUpUsername.trim();
    var password = this.state.signUpPassword.trim();
    if (!username || !password) {
      return;
    }
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/user',
      data: JSON.stringify({ username: username, password: password }),
      success: function (data) {
        console.log(data);
        $('#signUpCloseButton').click();
        this.setState({ signUpUsername: '', signUpPassword: '' });
        this.setState({ username: username, password: password });
        $('#retypePasswordSignup').val('');
        $('#signInButton').click();
      }.bind(this),
      dataType: 'json'
    });
  },
  render: function () {
    var navbarForm;
    if (this.state.username) {
      navbarForm = React.createElement(NavbarFormLoggedIn, { parent: this });
    } else {
      navbarForm = React.createElement(NavbarFormLoggedOut, { parent: this });
    }
    return React.createElement(
      'div',
      { className: 'navbar-wrapper' },
      React.createElement(
        'nav',
        { className: 'navbar navbar-inverse navbar-fixed-top' },
        React.createElement(
          'div',
          { className: 'container' },
          React.createElement(
            'div',
            { className: 'navbar-header' },
            React.createElement(
              'button',
              { type: 'button', className: 'navbar-toggle collapsed', 'data-toggle': 'collapse', 'data-target': '#navbar', 'aria-expanded': 'false', 'aria-controls': 'navbar' },
              React.createElement(
                'span',
                { className: 'sr-only' },
                'Toggle navigation'
              ),
              React.createElement('span', { className: 'icon-bar' }),
              React.createElement('span', { className: 'icon-bar' }),
              React.createElement('span', { className: 'icon-bar' })
            ),
            React.createElement(
              'a',
              { className: 'navbar-brand', href: '/FA/' },
              'Financial Administration'
            )
          ),
          React.createElement(
            'div',
            { id: 'navbar', className: 'navbar-collapse collapse' },
            navbarForm
          )
        )
      ),
      React.createElement(
        'form',
        { role: 'form', 'data-toggle': 'validator', onSubmit: this.handleSignUpSubmit },
        React.createElement(
          'div',
          { className: 'modal fade', role: 'dialog', id: 'signUpModal' },
          React.createElement(
            'div',
            { className: 'modal-dialog' },
            React.createElement(
              'div',
              { className: 'modal-content' },
              React.createElement(
                'div',
                { className: 'modal-header' },
                React.createElement(
                  'button',
                  { type: 'button', className: 'close', 'data-dismiss': 'modal', 'aria-label': 'Close' },
                  React.createElement(
                    'span',
                    { 'aria-hidden': 'true' },
                    '×'
                  )
                ),
                React.createElement(
                  'h4',
                  { className: 'modal-title' },
                  'Sign up'
                )
              ),
              React.createElement(
                'div',
                { className: 'modal-body' },
                React.createElement(
                  'div',
                  { className: 'form-group' },
                  React.createElement(
                    'label',
                    { className: 'control-label' },
                    'Username:'
                  ),
                  React.createElement('input', {
                    type: 'text',
                    className: 'form-control',
                    value: this.state.signUpUsername,
                    onChange: this.handleSignUpUsernameChange,
                    autofocus: true,
                    required: true })
                ),
                React.createElement(
                  'div',
                  { className: 'form-group' },
                  React.createElement(
                    'label',
                    { className: 'control-label' },
                    'Password:'
                  ),
                  React.createElement('input', {
                    type: 'password',
                    className: 'form-control',
                    id: 'passwordSignup',
                    'data-minlength': '6',
                    value: this.state.signUpPassword,
                    onChange: this.handleSignUpPasswordChange,
                    required: true }),
                  React.createElement(
                    'span',
                    { className: 'help-block' },
                    'Minimum of 6 characters'
                  )
                ),
                React.createElement(
                  'div',
                  { className: 'form-group' },
                  React.createElement(
                    'label',
                    { className: 'control-label' },
                    'Retype password:'
                  ),
                  React.createElement('input', { type: 'password', id: 'retypePasswordSignup', className: 'form-control', 'data-match': '#passwordSignup', 'data-match-error': 'Whoops, these don\'t match', required: true }),
                  React.createElement('div', { className: 'help-block with-errors' })
                )
              ),
              React.createElement(
                'div',
                { className: 'modal-footer' },
                React.createElement(
                  'button',
                  { type: 'button', className: 'btn btn-default', 'data-dismiss': 'modal', id: 'signUpCloseButton' },
                  'Close'
                ),
                React.createElement(
                  'button',
                  { type: 'submit', className: 'btn btn-primary' },
                  'Register'
                )
              )
            )
          )
        )
      ),
      React.createElement(Accounts, { parent: this })
    );
  }
});

var Account = React.createClass({
  displayName: 'Account',

  getInitialState: function () {
    return { account: {}, operations: [] };
  },
  getAccount: function () {
    if (this.props.accountNumber) {
      $.get('/FA/api/account/' + this.props.accountNumber, function (data) {
        this.setState({ account: data });
      }.bind(this));
    } else {
      this.setState({ account: {} });
    }
  },
  getOperations: function () {
    if (this.props.accountNumber) {
      $.get('/FA/api/operation/all/' + this.props.accountNumber, function (data) {
        this.setState({ operations: data });
      }.bind(this));
    } else {
      this.setState({ operations: [] });
    }
  },
  componentDidMount: function () {
    this.getAccount();
    this.getOperations();
    this.props.parent.state.intervalIds.push(setInterval(this.getAccount, 500));
    this.props.parent.state.intervalIds.push(setInterval(this.getOperations, 500));
  },
  componentWillReceiveProps: function (nextProps) {
    this.getAccount();
    this.getOperations();
  },
  render: function () {
    var rows = [];
    this.state.operations.forEach(function (operation) {
      if (operation.fromAccount.accountNumber == operation.toAccount.accountNumber) {
        if (operation.amount < 0) {
          rows.push(React.createElement(
            'tr',
            { className: 'danger' },
            React.createElement(
              'td',
              null,
              moment(operation.createTimestamp).format('DD.MM.YYYY HH:mm')
            ),
            React.createElement(
              'td',
              null,
              operation.fromAccount.accountNumber
            ),
            React.createElement(
              'td',
              null,
              operation.amount.toFixed(2)
            )
          ));
        } else {
          rows.push(React.createElement(
            'tr',
            { className: 'success' },
            React.createElement(
              'td',
              null,
              moment(operation.createTimestamp).format('DD.MM.YYYY HH:mm')
            ),
            React.createElement(
              'td',
              null,
              operation.toAccount.accountNumber
            ),
            React.createElement(
              'td',
              null,
              '+',
              operation.amount.toFixed(2)
            )
          ));
        }
      } else {
        if (operation.fromAccount.accountNumber === this.props.accountNumber) {
          rows.push(React.createElement(
            'tr',
            { className: 'danger' },
            React.createElement(
              'td',
              null,
              moment(operation.createTimestamp).format('DD.MM.YYYY HH:mm')
            ),
            React.createElement(
              'td',
              null,
              operation.toAccount.accountNumber
            ),
            React.createElement(
              'td',
              null,
              '-',
              operation.amount.toFixed(2)
            )
          ));
        } else {
          rows.push(React.createElement(
            'tr',
            { className: 'success' },
            React.createElement(
              'td',
              null,
              moment(operation.createTimestamp).format('DD.MM.YYYY HH:mm')
            ),
            React.createElement(
              'td',
              null,
              operation.fromAccount.accountNumber
            ),
            React.createElement(
              'td',
              null,
              '+',
              operation.amount.toFixed(2)
            )
          ));
        }
      }
    }.bind(this));
    var balance;
    if (this.state.account.balance || this.state.account.balance === 0) {
      if (this.state.account.balance >= 0) {
        balance = React.createElement(
          'td',
          null,
          React.createElement(
            'strong',
            null,
            React.createElement(
              'text',
              { className: 'text-success' },
              '+',
              this.state.account.balance.toFixed(2)
            )
          )
        );
      } else {
        balance = React.createElement(
          'td',
          null,
          React.createElement(
            'strong',
            null,
            React.createElement(
              'text',
              { className: 'text-danger' },
              this.state.account.balance.toFixed(2)
            )
          )
        );
      }
    }
    if (this.props.accountNumber) {
      return React.createElement(
        'div',
        { className: 'account' },
        React.createElement(
          'table',
          { className: 'table table-condensed' },
          React.createElement(
            'thead',
            null,
            React.createElement(
              'tr',
              null,
              React.createElement(
                'th',
                null,
                'Date'
              ),
              React.createElement(
                'th',
                null,
                'Account'
              ),
              React.createElement(
                'th',
                null,
                'Amount'
              )
            )
          ),
          React.createElement(
            'tbody',
            null,
            rows
          ),
          React.createElement(
            'tfoot',
            null,
            React.createElement(
              'tr',
              null,
              React.createElement('td', null),
              React.createElement(
                'td',
                { className: 'alnright' },
                React.createElement(
                  'strong',
                  null,
                  '='
                )
              ),
              balance
            )
          )
        )
      );
    } else {
      return React.createElement('div', { className: 'account' });
    }
  }
});

var Menu = React.createClass({
  displayName: 'Menu',

  getInitialState: function () {
    return {
      transferTo: '',
      transferAmount: '',
      depositAmount: '',
      withdrawAmount: ''
    };
  },
  handleTransferSubmit: function (e) {
    e.preventDefault();
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/operation/transfer',
      data: JSON.stringify({ fromAccountNumber: this.props.accountNumber, toAccountNumber: this.state.transferTo, amount: this.state.transferAmount }),
      success: function (data) {
        console.log(data);
        $("#transferCloseButton").click();
        this.setState({ transferTo: '', transferAmount: '' });
      }.bind(this),
      dataType: 'json'
    });
  },
  handleTransferToChange: function (e) {
    this.setState({ transferTo: e.target.value });
  },
  handleTransferAmountChange: function (e) {
    this.setState({ transferAmount: e.target.value });
  },
  handleDepositSubmit: function (e) {
    e.preventDefault();
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/operation/deposit',
      data: JSON.stringify({ accountNumber: this.props.accountNumber, amount: this.state.depositAmount }),
      success: function (data) {
        console.log(data);
        $("#depositCloseButton").click();
        this.setState({ depositAmount: '' });
      }.bind(this),
      dataType: 'json'
    });
  },
  handleDepositAmountChange: function (e) {
    this.setState({ depositAmount: e.target.value });
  },
  handleWithdrawSubmit: function (e) {
    e.preventDefault();
    $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: '/FA/api/operation/withdraw',
      data: JSON.stringify({ accountNumber: this.props.accountNumber, amount: this.state.withdrawAmount }),
      success: function (data) {
        console.log(data);
        $("#withdrawCloseButton").click();
        this.setState({ withdrawAmount: '' });
      }.bind(this),
      dataType: 'json'
    });
  },
  handleWithdrawAmountChange: function (e) {
    this.setState({ withdrawAmount: e.target.value });
  },
  render: function () {
    if (this.props.accountNumber) {
      return React.createElement(
        'div',
        { className: 'menu' },
        React.createElement(
          'button',
          { className: 'btn btn-default', 'data-toggle': 'modal', 'data-target': '#transferModal' },
          'Transfer'
        ),
        ' ',
        React.createElement(
          'button',
          { className: 'btn btn-default', 'data-toggle': 'modal', 'data-target': '#depositModal' },
          'Deposit'
        ),
        ' ',
        React.createElement(
          'button',
          { className: 'btn btn-default', 'data-toggle': 'modal', 'data-target': '#withdrawModal' },
          'Withdraw'
        ),
        React.createElement(
          'form',
          { onSubmit: this.handleTransferSubmit },
          React.createElement(
            'div',
            { className: 'modal fade', role: 'dialog', id: 'transferModal' },
            React.createElement(
              'div',
              { className: 'modal-dialog' },
              React.createElement(
                'div',
                { className: 'modal-content' },
                React.createElement(
                  'div',
                  { className: 'modal-header' },
                  React.createElement(
                    'button',
                    { type: 'button', className: 'close', 'data-dismiss': 'modal', 'aria-label': 'Close' },
                    React.createElement(
                      'span',
                      { 'aria-hidden': 'true' },
                      '×'
                    )
                  ),
                  React.createElement(
                    'h4',
                    { className: 'modal-title' },
                    'Transfer'
                  )
                ),
                React.createElement(
                  'div',
                  { className: 'modal-body' },
                  React.createElement(
                    'div',
                    { className: 'form-group' },
                    React.createElement(
                      'label',
                      null,
                      'Transfer to:'
                    ),
                    React.createElement('input', {
                      type: 'text',
                      className: 'form-control',
                      value: this.state.transferTo,
                      onChange: this.handleTransferToChange
                    })
                  ),
                  React.createElement(
                    'div',
                    { className: 'form-group' },
                    React.createElement(
                      'label',
                      null,
                      'Transfer amount:'
                    ),
                    React.createElement('input', {
                      type: 'text',
                      className: 'form-control',
                      value: this.state.transferAmount,
                      onChange: this.handleTransferAmountChange
                    })
                  )
                ),
                React.createElement(
                  'div',
                  { className: 'modal-footer' },
                  React.createElement(
                    'button',
                    { type: 'button', className: 'btn btn-default', 'data-dismiss': 'modal', id: 'transferCloseButton' },
                    'Close'
                  ),
                  React.createElement('input', {
                    type: 'submit',
                    className: 'btn btn-primary',
                    value: 'Transfer'
                  })
                )
              )
            )
          )
        ),
        React.createElement(
          'form',
          { onSubmit: this.handleDepositSubmit },
          React.createElement(
            'div',
            { className: 'modal fade', role: 'dialog', id: 'depositModal' },
            React.createElement(
              'div',
              { className: 'modal-dialog' },
              React.createElement(
                'div',
                { className: 'modal-content' },
                React.createElement(
                  'div',
                  { className: 'modal-header' },
                  React.createElement(
                    'button',
                    { type: 'button', className: 'close', 'data-dismiss': 'modal', 'aria-label': 'Close' },
                    React.createElement(
                      'span',
                      { 'aria-hidden': 'true' },
                      '×'
                    )
                  ),
                  React.createElement(
                    'h4',
                    { className: 'modal-title' },
                    'Deposit'
                  )
                ),
                React.createElement(
                  'div',
                  { className: 'modal-body' },
                  React.createElement(
                    'div',
                    { className: 'form-group' },
                    React.createElement(
                      'label',
                      null,
                      'Deposit amount:'
                    ),
                    React.createElement('input', {
                      type: 'text',
                      className: 'form-control',
                      value: this.state.depositAmount,
                      onChange: this.handleDepositAmountChange
                    })
                  )
                ),
                React.createElement(
                  'div',
                  { className: 'modal-footer' },
                  React.createElement(
                    'button',
                    { type: 'button', className: 'btn btn-default', 'data-dismiss': 'modal', id: 'depositCloseButton' },
                    'Close'
                  ),
                  React.createElement('input', {
                    type: 'submit',
                    className: 'btn btn-primary',
                    value: 'Deposit'
                  })
                )
              )
            )
          )
        ),
        React.createElement(
          'form',
          { onSubmit: this.handleWithdrawSubmit },
          React.createElement(
            'div',
            { className: 'modal fade', role: 'dialog', id: 'withdrawModal' },
            React.createElement(
              'div',
              { className: 'modal-dialog' },
              React.createElement(
                'div',
                { className: 'modal-content' },
                React.createElement(
                  'div',
                  { className: 'modal-header' },
                  React.createElement(
                    'button',
                    { type: 'button', className: 'close', 'data-dismiss': 'modal', 'aria-label': 'Close' },
                    React.createElement(
                      'span',
                      { 'aria-hidden': 'true' },
                      '×'
                    )
                  ),
                  React.createElement(
                    'h4',
                    { className: 'modal-title' },
                    'Withdraw'
                  )
                ),
                React.createElement(
                  'div',
                  { className: 'modal-body' },
                  React.createElement(
                    'div',
                    { className: 'form-group' },
                    React.createElement(
                      'label',
                      null,
                      'Withdraw amount:'
                    ),
                    React.createElement('input', {
                      type: 'text',
                      className: 'form-control',
                      value: this.state.withdrawAmount,
                      onChange: this.handleWithdrawAmountChange
                    })
                  )
                ),
                React.createElement(
                  'div',
                  { className: 'modal-footer' },
                  React.createElement(
                    'button',
                    { type: 'button', className: 'btn btn-default', 'data-dismiss': 'modal', id: 'withdrawCloseButton' },
                    'Close'
                  ),
                  React.createElement('input', {
                    type: 'submit',
                    className: 'btn btn-primary',
                    value: 'Withdraw'
                  })
                )
              )
            )
          )
        )
      );
    } else {
      return React.createElement('div', { className: 'menu' });
    }
  }
});

var ChildAccounts = React.createClass({
  displayName: 'ChildAccounts',

  render: function () {
    return React.createElement(
      'div',
      { className: 'panel panel-default' },
      React.createElement(
        'div',
        { className: 'panel-heading', role: 'tab', id: 'headingOne' },
        React.createElement(
          'h4',
          { className: 'panel-title' },
          React.createElement(
            'a',
            { role: 'button', id: this.props.account.accountNumber, onClick: this.props.parent.handleThatEvent.bind(this, this.props.account.accountNumber), 'data-toggle': 'collapse', 'data-parent': '#accordion', href: "#collapse" + this.props.account.accountNumber, 'aria-expanded': 'false', 'aria-controls': "collapse" + this.props.account.accountNumber },
            this.props.account.accountNumber
          )
        )
      ),
      React.createElement(
        'div',
        { id: "collapse" + this.props.account.accountNumber, className: 'panel-collapse collapse', role: 'tabpanel', 'aria-labelledby': "heading" + this.props.account.accountNumber },
        React.createElement(
          'div',
          { className: 'panel-body' },
          React.createElement(Account, { parent: this.props.parent, accountNumber: this.props.parent.state.selectedAccountNumber })
        )
      )
    );
  }
});

var Accounts = React.createClass({
  displayName: 'Accounts',

  getInitialState: function () {
    return { accounts: [], selectedAccountNumber: '', intervalIds: [] };
  },
  getAccounts: function () {
    $.get('/FA/api/account/all/' + this.props.parent.state.username, function (data) {
      this.setState({ accounts: data });
    }.bind(this));
  },
  componentWillReceiveProps: function (nextProps) {
    if (this.props.parent.state.username) {
      this.getAccounts();
    } else {
      this.setState({ accounts: [], selectedAccountNumber: '' });
      this.state.intervalIds.forEach(function (intervalId) {
        clearInterval(intervalId);
      });
    }
  },
  handleThatEvent: function (accountNumber, e) {
    this.setState({ selectedAccountNumber: accountNumber });
  },
  render: function () {
    var collapses = [];
    this.state.accounts.forEach(function (account) {
      collapses.push(React.createElement(ChildAccounts, { account: account, parent: this }));
    }.bind(this));
    return React.createElement(
      'div',
      { className: 'accounts' },
      React.createElement(
        'div',
        { className: 'panel-group', id: 'accordion', role: 'tablist', 'aria-multiselectable': 'true' },
        collapses
      ),
      React.createElement(Menu, { accountNumber: this.state.selectedAccountNumber })
    );
  }
});

var Footer = React.createClass({
  displayName: 'Footer',

  render: function () {
    return React.createElement(
      'div',
      { className: 'footer' },
      React.createElement('hr', null),
      React.createElement(
        'div',
        { className: 'footer-content' },
        React.createElement(
          'p',
          null,
          'Copyright © Bernhard FRITZ & Mario ZELGER 2016'
        )
      )
    );
  }
});

var Component = React.createClass({
  displayName: 'Component',

  render: function () {
    return React.createElement(
      'div',
      { className: 'component' },
      React.createElement(Navbar, { url: '/FA/api/user/current' }),
      React.createElement(Footer, null)
    );
  }
});

ReactDOM.render(React.createElement(Component, null), document.getElementById('content'));
/* jshint ignore:end */

},{}]},{},[1]);
