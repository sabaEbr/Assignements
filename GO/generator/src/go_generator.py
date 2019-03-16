import sys
from ruamel.yaml import YAML
from .java_util import *


class GoGen:

    def __init__(self, program_path):
        yaml = YAML()
        self.program = yaml.load(program_path)
        self.project_path = self.program['project_path']
        self.prod_path = self.program['prod_path']

        self.switcher = {
            'java': self.java_work,
            'go': self.go_work
        }

    def __call__(self):
        [self.switcher.get(lang, self.nothing)(self.program['sequencer'][lang]) for lang in self.program['sequencer']]

    def java_work(self, java_sequence):
        for name, source_path, java_file, class_path, class_output, jar_content, manifest in java_sequence.items():
            java_build(name, source_path, java_file, class_output, class_path)
            java_lib(name, class_output, jar_content, self.prod_path, manifest)

    def go_work(self, go_sequence): pass

    def nothing(self): pass





if __name__ == "__main__":
    GoGen(sys.argv[1])()