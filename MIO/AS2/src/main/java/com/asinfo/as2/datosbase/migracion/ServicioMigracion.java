package com.asinfo.as2.datosbase.migracion;

import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
import com.asinfo.as2.entities.DetalleTomaFisica;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.OrdenSalidaMaterial;
import com.asinfo.as2.entities.OrganizacionConfiguracion;
import com.asinfo.as2.entities.PagoRol;
import com.asinfo.as2.entities.PlanComision;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.VersionPlanComision;
import com.asinfo.as2.entities.aerolineas.Ticket;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

@Local
public abstract interface ServicioMigracion
{
  public abstract void migracionPlanDeCuentas(int paramInt1, String paramString, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void migracionClientesProveedores(int paramInt1, int paramInt2, String paramString, InputStream paramInputStream, int paramInt3, List<Empresa> paramList)
    throws ExcepcionAS2, ExcepcionAS2Identification, AS2Exception;
  
  public abstract void migracionProductos(int paramInt1, int paramInt2, String paramString, InputStream paramInputStream, int paramInt3, List<Producto> paramList)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void migracionDimensionesContables(int paramInt1, String paramString1, String paramString2, InputStream paramInputStream, int paramInt2, List<SelectItem> paramList)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void migracionEmpleado(InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract void migracionCargaEmpleado(InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<FacturaProveedor> migracionFacturaProveedor(String paramString, InputStream paramInputStream, int paramInt1, int paramInt2)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void migracionVersionListaPrecios(Date paramDate, int paramInt1, String paramString, InputStream paramInputStream, int paramInt2, int paramInt3, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract void subidaRubrosVariablesEmpleado(PagoRol paramPagoRol, int paramInt1, String paramString, InputStream paramInputStream, int paramInt2)
    throws IOException;
  
  public abstract void migracionVacacionEmpleado(InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract void migracionLote(String paramString, InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3, OrganizacionConfiguracion paramOrganizacionConfiguracion)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void migracionProductoAtributo(String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract void cargaListaDeMateriales(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract void cargarAsignarRubros(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List<Ticket> migracionCargaTicket(InputStream paramInputStream, int paramInt1, int paramInt2, int paramInt3, Integer paramInteger)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void cargarTomaFisica(int paramInt1, InputStream paramInputStream, int paramInt2, List<DetalleTomaFisica> paramList, List<String> paramList1)
    throws ExcepcionAS2, IOException;
  
  public abstract void migracionPartidasPresupuestarias(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void asignacionPartidasPresupuestarias(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void migracionListaDescuentos(Date paramDate, int paramInt1, String paramString, InputStream paramInputStream, int paramInt2, int paramInt3, int paramInt4)
    throws ExcepcionAS2;
  
  public abstract void migracionLecturaMantenimiento(int paramInt1, int paramInt2, String paramString, InputStream paramInputStream, int paramInt3)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void migracionEquipos(int paramInt1, String paramString, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract void migracionCustodioActivoFijo(int paramInt1, int paramInt2, String paramString, InputStream paramInputStream, int paramInt3)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void cargarOrdenSalidaMaterial(int paramInt1, InputStream paramInputStream, int paramInt2, List<DetalleOrdenSalidaMaterial> paramList, OrdenSalidaMaterial paramOrdenSalidaMaterial)
    throws ExcepcionAS2, IOException, AS2Exception;
  
  public abstract void migrarListaDetalleVersionPlanComision(PlanComision paramPlanComision, VersionPlanComision paramVersionPlanComision, String paramString, InputStream paramInputStream, int paramInt1, int paramInt2)
    throws AS2Exception, ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.migracion.ServicioMigracion
 * JD-Core Version:    0.7.0.1
 */