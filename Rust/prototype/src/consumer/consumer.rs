use crate::structs::bar::Bar;
use crate::structs::foo::Foo;

pub trait Consumer {
    fn consume_foo_data(&self, foo_data: Foo) -> ();
    fn consume_bar_data(&self, bar_data: Bar) -> ();
}