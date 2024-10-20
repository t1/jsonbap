package tck;

import com.github.t1.jsonbap.api.Bindable;

@Bindable(serializable = false, value = {
        ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedDateContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedDoubleContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedNillableContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedNillablePropertyContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedPropertyOrderContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedPropertyVisibilityContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedSerializedArrayContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimpleAnnotatedSerializedContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimpleContainer.class,
        ee.jakarta.tck.json.bind.api.model.SimplePartiallyAnnotatedPropertyOrderContainer.class,
        ee.jakarta.tck.json.bind.cdi.customizedmapping.adapters.model.AnimalShelterInjectedAdapter.class,
        ee.jakarta.tck.json.bind.cdi.customizedmapping.serializers.model.AnimalShelterWithInjectedSerializer.class,
        ee.jakarta.tck.json.bind.customizedmapping.adapters.model.Animal.class,
        ee.jakarta.tck.json.bind.customizedmapping.adapters.model.AnimalShelter.class,
        ee.jakarta.tck.json.bind.customizedmapping.adapters.model.AnimalShelterAdapted.class,
        ee.jakarta.tck.json.bind.customizedmapping.adapters.model.Cat.class,
        ee.jakarta.tck.json.bind.customizedmapping.adapters.model.Dog.class,
        ee.jakarta.tck.json.bind.customizedmapping.binarydata.model.BinaryDataContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.ijson.model.BinaryDataContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.nillable.NillablePackageNillablePropertyNonNillableContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.nillable.NillablePackageNonNillablePropertyContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.nillable.NillablePackageSimpleContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.NillableContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.NillablePropertyContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.NillablePropertyNonNillableContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.nonnillable.NonNillablePackageNillableContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.nonnillable.NonNillablePackageNonNillablePropertyNillableContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.nonnillable.NonNillablePackageSimpleContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.NonNillableAndNillablePropertyContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.NonNillableContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.NonNillablePropertyContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.NonNillablePropertyNillableContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.nullhandling.model.SimpleContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.DuplicateNameContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientGetterPlusCustomizationAnnotatedFieldContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientGetterPlusCustomizationAnnotatedGetterContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientPlusCustomizationAnnotatedGetterContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientPlusCustomizationAnnotatedPropertyContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientPlusCustomizationAnnotatedSetterContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientSetterPlusCustomizationAnnotatedFieldContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.propertynames.model.TransientSetterPlusCustomizationAnnotatedSetterContainer.class,
        ee.jakarta.tck.json.bind.customizedmapping.serializers.model.Animal.class,
        ee.jakarta.tck.json.bind.customizedmapping.serializers.model.AnimalShelter.class,
        ee.jakarta.tck.json.bind.customizedmapping.serializers.model.AnimalShelterWithSerializer.class,
        ee.jakarta.tck.json.bind.customizedmapping.serializers.model.Cat.class,
        ee.jakarta.tck.json.bind.customizedmapping.serializers.model.Dog.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.BooleanContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.ByteContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.CharacterContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.DoubleContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.FloatContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.IntegerContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.NumberContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.ShortContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.basictypes.model.StringContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerAccessorsWithoutMatchingField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerFinalField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerFinalPublicField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerNoAccessorsPackagePrivateField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerNoAccessorsPrivateField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerNoAccessorsProtectedField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerNoAccessorsPublicField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPackagePrivateAccessors.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPackagePrivateConstructor.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPrivateAccessors.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPrivateAccessorsPublicField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPrivateConstructor.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerProtectedAccessors.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerProtectedConstructor.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerProtectedStaticNestedClass.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPublicAccessors.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPublicAccessorsPublicField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPublicConstructor.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerPublicStaticNestedClass.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerStaticField.class,
        ee.jakarta.tck.json.bind.defaultmapping.classes.model.StringContainerTransientField.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.CalendarContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.DateContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.DurationContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.GregorianCalendarContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.InstantContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.LocalDateContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.LocalDateTimeContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.LocalTimeContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.OffsetDateTimeContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.OffsetTimeContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.PeriodContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.SimpleTimeZoneContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.TimeZoneContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.ZonedDateTimeContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.ZoneIdContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.dates.model.ZoneOffsetContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.enums.model.EnumContainer.Enumeration.class,
        ee.jakarta.tck.json.bind.defaultmapping.enums.model.EnumContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.generics.model.MultipleBoundsContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.identifiers.model.StringContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.AnnotationTypeInfoTest.Animal.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.AnnotationTypeInfoTest.Cat.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.AnnotationTypeInfoTest.Dog.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.model.StringContainerSubClass.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.MultipleTypeInfoTest.Car.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.MultipleTypeInfoTest.Labrador.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.TypeInfoExceptionsTest.Dog.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.TypeInfoExceptionsTest.InvalidAlias.class,
        ee.jakarta.tck.json.bind.defaultmapping.polymorphictypes.TypeInfoExceptionsTest.PropertyNameCollision.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.BigDecimalContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.BigIntegerContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.OptionalArrayContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.OptionalContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.OptionalDoubleContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.OptionalIntContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.OptionalLongContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.OptionalTypeContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.SimpleContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.URIContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.specifictypes.model.URLContainer.class,
        ee.jakarta.tck.json.bind.defaultmapping.uniqueness.model.SimpleContainer.class,
        ee.jakarta.tck.json.bind.TypeContainer.class,
})
public class Setup {
    public static void main(String[] args) {
        System.out.println("This class exists only to trigger the @Jsonb annotation processor");
    }
}
