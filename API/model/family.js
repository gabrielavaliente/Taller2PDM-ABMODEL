const mongoose = require('mongoose');

const familySchema = new mongoose.Schema({
    family_name: {
        required: true,
        type: String
    },
    community: {
        required: true,
        type: String
    },
    houseType: {
        required: true,
        type: String
    },
    risk: {
        required: true,
        type: String
    }
});

module.exports = mongoose.model('Familia', familySchema);