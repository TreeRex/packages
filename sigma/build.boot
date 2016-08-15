;;;; -*- Mode:clojure; -*-
(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.2" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.1.0")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom {:project     'cljsjs/sigma
      :version     +version+
      :description "A Markdown to HTML converter written in Javascript"
      :url         "http://sigmajs.org/"
      :scm         {:url "https://github.com/jacomyal/sigma.js"}
      :license     {"MIT" "https://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url (format "https://github.com/jacomyal/sigma.js/releases/download/v%s/release-v%s.zip"
                           +lib-version+ +lib-version+)
			 :checksum  "49a3785b129da42877f15a77e0dca6c2"
			 :unzip     true)
	(sift :move {#"^release.*/build/sigma.js" "cljsjs/sigma/development/sigma.inc.js"
	             #"^release.*/build/sigma.min.js" "cljsjs/sigma/production/sigma.min.inc.js"})
	(sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.sigma")
	(pom)
	(jar)))
