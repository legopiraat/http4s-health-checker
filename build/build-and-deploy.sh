#!/usr/bin/env bash

# Build the docker image
docker build -t kubernetes-health-checks:latest ../

# Make sure the old helm chart is deleted.
helm del --purge kubernetes-health-checks

# Install the new helm chart.
helm install --name kubernetes-health-checks helm/kubernetes-health-checks