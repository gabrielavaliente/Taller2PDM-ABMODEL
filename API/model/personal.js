const mongoose = require('mongoose');

const personalSchema = new mongoose.Schema({
    dui: {
        required: false,
        type: String
    },
    name: {
        required: true,
        type: String
    },
    birthdate: {
        required: true,
        type: Date
    },
    grade: {
        required: true,
        type: String
    },
    read:{
        required: true,
        type: Boolean
    },
    write: {
        required: true,
        type: Boolean
    },
    family: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Familia',
        required: true
    }
});

module.exports = mongoose.model('person', personalSchema);