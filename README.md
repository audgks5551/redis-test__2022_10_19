# redis-config.yaml
```yml
apiVersion: v1
kind: ConfigMap
metadata:
  name: redis-configmap
data:
  redis-config: |
    maxmemory 2mb
    maxmemory-policy allkeys-lru
    bind 0.0.0.0
    requirepass 1111
---
apiVersion: v1
kind: Pod
metadata:
  name: redis
  labels:
    app: redis
spec:
  containers:
    - name: redis
      image: redis:5.0.4
      command:
        - redis-server
        - "/redis-master/redis.conf"
      env:
        - name: MASTER
          value: "true"
      ports:
        - containerPort: 6379
      resources:
        limits:
          cpu: "0.1"
      volumeMounts:
        - mountPath: /redis-master-data
          name: data
        - mountPath: /redis-master
          name: config
  volumes:
    - name: data
      emptyDir: {}
    - name: config
      configMap:
        name: redis-configmap
        items:
          - key: redis-config
            path: redis.conf
---
apiVersion: v1
kind: Service
metadata:
  name: redis
  labels:
    app: redis
spec:
  type: NodePort
  selector:
    app: redis
  ports:
    - name: redis
      protocol: TCP
      port: 6379
      targetPort: 6379
      nodePort: 30003
```