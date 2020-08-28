package com.tsoft.civilization.ai;

import org.junit.jupiter.api.Test;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.Perceptron;

import java.util.Arrays;

public class NeurophTest {
    @Test
    public void or() {
        train();

        double[] res = calculate(1, 1);
        Arrays.stream(res).forEach(System.out::println);

        res = calculate(0, 0);
        Arrays.stream(res).forEach(System.out::println);

        res = calculate(0.3, 0.2);
        Arrays.stream(res).forEach(System.out::println);
    }

    private void train() {
        // create new perceptron network
        NeuralNetwork neuralNetwork = new Perceptron(2, 1);
        // create training set
        DataSet trainingSet = new  DataSet(2, 1);
        // add training data to training set (logical OR function)
        trainingSet.add(new double[]{0, 0}, new double[]{0});
        trainingSet.add(new double[]{0, 1}, new double[]{1});
        trainingSet.add(new double[]{1, 0}, new double[]{1});
        trainingSet.add(new double[]{1, 1}, new double[]{1});
        // learn the training set
        neuralNetwork.learn(trainingSet);
        // save the trained network into file
        neuralNetwork.save("or_perceptron.nnet");
    }

    private double[] calculate(double ... input) {
        // load the saved network
        NeuralNetwork neuralNetwork = NeuralNetwork.createFromFile("or_perceptron.nnet");
        // set network input
        neuralNetwork.setInput(input);
        // calculate network
        neuralNetwork.calculate();
        // get network output
        double[] networkOutput = neuralNetwork.getOutput();

        return networkOutput;
    }
}
