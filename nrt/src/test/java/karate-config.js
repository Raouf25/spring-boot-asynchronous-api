function init() {

    var env = karate.env;
    karate.log('karate.env selected environment was:', env)
    if (!env) {
        env = 'local';
    }

    var apiUrl = karate.properties['api.url'];

    console.log()

    var config = {
        env : env,
        baseUrl : apiUrl
    };

    karate.configure('connectTimeout', 500000)
    karate.configure('readTimeout', 500000)
    karate.configure('ssl', false)
    karate.configure('charset', null)

    return config;
}
