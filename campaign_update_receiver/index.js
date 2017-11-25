'use strict';

const Redis = require('redis');
const Subscriber = Redis.createClient({ url: process.env.REDIS_URI });
const CHANNEL = 'campaign-update';

Subscriber.on('ready', () => console.log('ready'));

Subscriber.on('error', (err) => console.error(err));

Subscriber.on('message', (channel, message) => {
    console.log('Campaign Update Arrived!');
    console.log(JSON.parse(message, 2, 2));
});

Subscriber.subscribe(CHANNEL);
