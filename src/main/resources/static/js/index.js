/*!
 * SCRIPT: index.js
 * AUTHOR: Wu (https://github.com/stupid-2020)
 * DATE  : 17-JAN-2022
 * NOTE  : 
 */
var app = new Vue({
    el: '#app',
    data: {
        card_bg: [
            'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/D%C3%BClmen%2C_Kirchspiel%2C_B%C3%B6rnste%2C_Felder_und_B%C3%A4ume_--_2017_--_3220-6.jpg/640px-D%C3%BClmen%2C_Kirchspiel%2C_B%C3%B6rnste%2C_Felder_und_B%C3%A4ume_--_2017_--_3220-6.jpg',
            'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Santorin_%28GR%29%2C_Exomytis%2C_Vlychada_Beach_--_2017_--_2999_%28bw%29.jpg/640px-Santorin_%28GR%29%2C_Exomytis%2C_Vlychada_Beach_--_2017_--_2999_%28bw%29.jpg',
            'https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/D%C3%BClmen%2C_Merfeld%2C_Feldweg_am_M%C3%BChlenbach_--_2021_--_4278-80.jpg/640px-D%C3%BClmen%2C_Merfeld%2C_Feldweg_am_M%C3%BChlenbach_--_2021_--_4278-80.jpg',
        ],
        authors: []
    },
    methods: {},
    mounted: function() {
        var _this = this;
        axios.get('api/getAuthors').then(
            function(response) {
                if (response.status == 200) {
                    _this.authors = response.data;
                    for (let author of _this.authors) {
                        author.bg = _this.card_bg[(author.id % 3)];
                    }
                }
            }
        ).catch(
            function(error) {
                if (error.response && (error.response.status == 401)) {
                    window.location.href = "/login";
                }
            }
        );
    },
    template: ' \
        <div class="container"> \
            <h1 class="text-center text-white my-4">Spring Boo<strike>t</strike>kstore</h1> \
            <div v-if="authors.length" class="row justify-content-md-center"> \
                <div \
                    v-for="author in authors" :key="author.id" \
                    class="card mx-1 px-0" \
                    style="width: 18rem;" \
                > \
                    <img :src="author.bg" class="card-img-top" style="height: 120px"> \
                    <div class="circle-avatar text-center bg-info"> \
                        <a \
                            :id="`tooltip_${author.id}`" \
                            :title="`${author.booknames}`" \
                            class="text-white" \
                            href="#" data-toggle="tooltip" \
                            style="text-decoration: none" \
                        > \
                            {{ author.initials }} \
                        </a> \
                    </div> \
                    <div class="card-body text-center"> \
                        <h5 class="card-title">{{ author.name }}</h5> \
                        <p \
                            class="card-text fw-lighter text-primary text-uppercase" \
                            style="font-size: .7rem" \
                        > \
                            {{ author.email }} \
                        </p> \
                    </div> \
                </div> \
            </div> \
            <div v-else> \
                <h2>No Spring Boot author!</h2> \
            </div> \
        </div> \
    '
})