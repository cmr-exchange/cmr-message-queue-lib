(defproject nasa-cmr/cmr-message-queue-lib "0.1.0-SNAPSHOT"
  :description "Library containing code to handle message queue interactions within the CMR."
  :url "https://github.com/nasa/Common-Metadata-Repository/tree/master/message-queue-lib"
  :exclusions [
    [cheshire]
    [clj-http]
    [clj-time]
    [commons-codec/commons-codec]
    [commons-io]
    [commons-logging]
    [org.apache.httpcomponents/httpclient]
    [org.apache.httpcomponents/httpcore]
    [org.clojure/tools.reader]
    [potemkin]]
  :dependencies [
    [cheshire "5.8.0"]
    [clj-http "2.3.0"]
    [clj-time "0.14.2"]
    [com.amazonaws/aws-java-sdk "1.11.261"]
    [com.novemberain/langohr "3.4.0"]
    [commons-codec/commons-codec "1.11"]
    [commons-io "2.6"]
    [commons-logging "1.2"]
    [nasa-cmr/cmr-acl-lib "0.1.0-SNAPSHOT"]
    [nasa-cmr/cmr-common-app-lib "0.1.0-SNAPSHOT"]
    [nasa-cmr/cmr-common-lib "0.1.1-SNAPSHOT"]
    [nasa-cmr/cmr-transmit-lib "0.1.0-SNAPSHOT"]
    [org.apache.httpcomponents/httpclient "4.5.4"]
    [org.apache.httpcomponents/httpcore "4.4.8"]
    [org.clojure/clojure "1.8.0"]
    [org.clojure/tools.reader "1.1.1"]
    [potemkin "0.4.4"]]
  :plugins [
    [lein-shell "0.5.0"]
    [test2junit "1.3.3"]]
  :jvm-opts ^:replace ["-server"
                       "-Dclojure.compiler.direct-linking=true"]
  :aot [cmr.message-queue.test.ExitException]
  :profiles {
    :dev {
      :exclusions [
        [org.clojure/tools.nrepl]]
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]
        [org.clojars.gjahad/debug-repl "0.3.3"]
        [org.clojure/tools.nrepl "0.2.13"]]
      :jvm-opts ^:replace ["-server"]
      :source-paths ["src" "dev" "test"]}
    :static {}
    ;; This profile is used for linting and static analysis. To run for this
    ;; project, use `lein lint` from inside the project directory. To run for
    ;; all projects at the same time, use the same command but from the top-
    ;; level directory.
    :lint {
      :source-paths ^:replace ["src"]
      :test-paths ^:replace []
      :plugins [
        [jonase/eastwood "0.2.5"]
        [lein-ancient "0.6.15"]
        [lein-bikeshed "0.5.0"]
        [lein-kibit "0.1.6"]
        [venantius/yagni "0.1.4"]]}
    ;; The following profile is overriden on the build server or in the user's
    ;; ~/.lein/profiles.clj file.
    :internal-repos {}}
  :aliases {
    ;; Alias to test2junit for consistency with lein-test-out
    "test-out" ["test2junit"]
    ;; Linting aliases
    "kibit"
      ["do"
        ["shell" "echo" "== Kibit =="]
        ["with-profile" "lint" "kibit"]]
    "eastwood"
      ["with-profile" "lint" "eastwood" "{:namespaces [:source-paths]}"]
    "bikeshed"
      ["with-profile" "lint" "bikeshed" "--max-line-length=100"]
    "yagni"
      ["with-profile" "lint" "yagni"]
    "check-deps"
      ["with-profile" "lint" "ancient" ":all"]
    "lint"
      ["do"
        ["check"] ["kibit"] ["eastwood"]]
    ;; Placeholder for future docs and enabler of top-level alias
    "generate-static" ["with-profile" "static" "shell" "echo"]
        ;; Run a local copy of SQS/SNS
    "start-sqs-sns"
      ["shell" "cmr" "start" "local" "sqs-sns"]
    "stop-sqs-sns"
      ["shell" "cmr" "stop" "local" "sqs-sns"]
    "restart-sqs-sns"
      ["do"
        ["stop-sqs-sns"]
        ["start-sqs-sns"]]})
