# add loki and promtail for cluster logs
helm upgrade --install loki grafana/loki-stack

# add library app and sidecar promtail
kubectl apply -f manifests

# set up ingress controller 
helm upgrade --install nginx ingress-nginx/ingress-nginx

# add prometheus and grafana
helm upgrade --install prometheus prometheus-community/kube-prometheus-stack -f manifests/prometheus-grafana-values.yml

# set up basic jenkins app
# helm upgrade --install jenkins jenkins/jenkins -f manifests/jenkins-values.yml