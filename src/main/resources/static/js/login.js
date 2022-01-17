/*!
 * SCRIPT: login.js
 * AUTHOR: Wu (https://github.com/stupid-2020)
 * DATE  : 17-JAN-2022
 * NOTE  : 
 */
var app = new Vue({
    el: '#app',
    data: {
        password: '',
        showPassword: false,
        username: '',
    },
    methods: {
        onSubmit: function(e) {
            var _this = this,
                data = {
                    username: _this.username,
                    password: _this.password
                };

            // Stop default FORM action
            e.preventDefault();
            axios.post(
                '/auth/signin',
                data
            ).then(
                function(response) {
                    if (response.status == 200) {
                        window.location.href = "/";
                    } else {
                        console.log(response);
                    }
                }
            ).catch(
                function(error) {
                    if (error.response.status == 401) {
                        alert(error.response.data.message);
                    }
                }
            );
        },
        onTogglePassword: function() {
            this.showPassword = !this.showPassword;
            inputPassword = this.$refs.password;
            if (this.showPassword) {
                inputPassword.setAttribute('type', 'text')
            } else {
                inputPassword.setAttribute('type', 'password')
            }
        }
    },
    mounted: function() {
        var _this = this;

        // Todo: add Authorization Header
        axios.post('/api/testAuth').then(
            function(response) {
                // Token is valid!  Redirect to Home
                if (response.status == 200) {
                    window.location.href = "/";
                }
            }
        ).catch(
            function(error) {
                _this.$refs.username.focus();
            }
        );
    },
    template: ' \
            <section class="login-section"> \
                <div class="container"> \
                    <div class="row justify-content-center"> \
                        <div class="col-md-6 text-center mb-5"> \
                            <h2 class="heading-section">Spring Boo<strike>t</strike>kstore</h2> \
                        </div> \
                    </div> \
                    <div class="row justify-content-center"> \
                        <div class="col-md-6 col-lg-4"> \
                            <div class="login-wrap p-0"> \
                                <h3 class="mb-4 text-center">Login</h3> \
                                <form class="signin-form"> \
                                    <div class="form-group"> \
                                        <input \
                                            ref="username" \
                                            type="text" \
                                            class="form-control" \
                                            placeholder="Username" \
                                            v-model="username" \
                                            required \
                                        > \
                                    </div> \
                                    <div class="form-group"> \
                                        <input \
                                            id="password-field" \
                                            ref="password" \
                                            type="password" \
                                            class="form-control" \
                                            placeholder="Password" \
                                            v-model="password" \
                                            required \
                                        > \
                                        <span \
                                            toggle="#password-field" \
                                            class="fa fa-fw field-icon toggle-password" \
                                            v-bind:class="[showPassword ? \'fa-eye-slash\' : \'fa-eye\']" \
                                            v-on:click="onTogglePassword" \
                                        > \
                                        </span> \
                                    </div> \
                                    <div class="form-group"> \
                                        <button \
                                            class="form-control btn btn-primary submit px-3" \
                                            v-on:click="onSubmit" \
                                        > \
                                            Sign In \
                                        </button> \
                                    </div> \
                                </form> \
                            </div> \
                        </div> \
                    </div> \
                </div> \
            </section> \
        '
})