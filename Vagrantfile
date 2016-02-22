VAGRANT_API_VERSION = 2

Vagrant.configure(VAGRANT_API_VERSION) do |config|
  config.vm.box = "ubuntu/trusty64"

  config.ssh.forward_agent = true

  config.vm.network "forwarded_port", guest: 3000, host: 3000 # web
  config.vm.network "forwarded_port", guest: 7000, host: 7000 # nrepl
  config.vm.network "forwarded_port", guest: 3449, host: 3449 # figwheel
  config.vm.network "forwarded_port", guest: 7002, host: 7002 # figwheel nrepl

  config.vm.network "private_network", type: "dhcp"

  config.vm.synced_folder ".", "/vagrant", type: "nfs"

  config.vm.provider "virtualbox" do |vb|
    vb.memory = 2048
    vb.cpus = 2
  end

  config.vm.provision "ansible" do |ansible|
    ansible.playbook = "cm/vagrant.yml"
  end
end
