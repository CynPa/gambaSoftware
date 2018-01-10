package com.asinfo.as2.inventario.reportes.servicio;

import com.asinfo.as2.entities.Atributo;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.ConjuntoAtributo;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteProductoAtributo
{
  public abstract List obtenerProductoAtributo(CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Atributo paramAtributo, ConjuntoAtributo paramConjuntoAtributo, int paramInt)
    throws ExcepcionAS2Inventario;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.ServicioReporteProductoAtributo
 * JD-Core Version:    0.7.0.1
 */