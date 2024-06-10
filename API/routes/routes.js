const express = require('express');
const personalModel = require('../model/personal');
const familyModel = require('../model/family');
const router = express.Router();

router.post('/postperson', async (req, res) => {
    const data = new personalModel ({
        dui: req.body.dui,
        name: req.body.name,
        birthdate: req.body.birthdate,
        grade: req.body.grade,
        read: req.body.read,
        write: req.body.write,
        family: req.body.family
    });

    try{
        const dataToSave = await data.save();
        res.status(200).json({"result": "ok"});
    } catch(error) {
        res.status(400).json({"message": error.message});
    }
});

router.get('/getallpersons', async (req, res) => {
    try{
        const data = await personalModel.find().populate('family');
    }catch (error) {
        res.status(400).json({"message": error.message});
    }
});

router.get('/getperson', async (req, res) => {
    try {
        const data = await personalModel.findById(req.query.id).populate('family')
        res.json(data);
    }catch (error) {
        res.status(400).json({"message": error.message});
    }
});

router.patch('/updateperson', async (req, res) => {
    try{
        const id = req.query.id;
        const updateData = req.body;
        const options = {new: true};

        const result = await personalModel.findByIdAndUpdate(
            id, updateData, options
        );
        res.status(200).json({"result": "ok"});
    }catch (error) {
        res.status(400).json({"message": error.message});
    }
});

router.delete('/deleteperson', async (req, res) => {
    try{
       const id = req.query.id;
       const result = await personalModel.findByIdAndDelete(id);
       res.status(200).json({"result": "ok"}); 
    } catch (error) {
       res.status(400).json({"message": error.message});
    }
});

//Familia

router.post('/postfamily', async (req, res) => {
    const data = new familyModel({
        family_name: req.body.family_name,
        community: req.body.community,
        housetype: req.body.houseType,
        risk: req.body.risk
    });

    try {
        const dataToSave = await data.save();
        res.status(200).json({"result": "ok"}); 
    } catch (error) {
        res.status(400).json({"message": error.message});
    }
});

router.get('/getallfamilies', async (req, res) => {
    try {
        const data = await familyModel.find();
        res.json(data);
    } catch (error) {
        res.status(400).json({"message": error.message});
    }
});

router.get('/getfamily', async (req, res) => {
    try {
        const data = await familyModel.findById(req.query.id);
        res.json(data);
    } catch (error) {
        res.status(400).json({"message": error.message});
    }
});

router.patch('/updatefamily', async (req , res) => {
    try {
        const id = req.query.id;
        const updateData = req.body;
        const options = { new: true };
    
        const result = await familyModel.findByIdAndUpdate(
            id, updateData, options
        );
    
        res.status(200).json({"result": "ok"}); 
    } catch (error) {
        res.status(400).json({"message": error.message});
    }
});

router.delete('/deletefamily', async (req, res) => {
    try {
        const id = req.query.id;
        const result = await familyModel.findByIdAndDelete(id);
        res.status(200).json({"result": "ok"}); 
    } catch (error) {
        res.status(400).json({"message": error.message});
    }
});

module.exports = router;