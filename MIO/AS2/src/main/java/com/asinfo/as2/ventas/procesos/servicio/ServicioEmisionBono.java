package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.DetalleFacturaCliente;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Especialidad;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.PersonaResponsable;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import com.asinfo.as2.excepciones.AS2Exception;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEmisionBono
{
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<DetalleFacturaCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void asignarNumeroBono(FacturaCliente paramFacturaCliente);
  
  public abstract List<DetalleFacturaCliente> listaBonos(Date paramDate1, Date paramDate2, Empresa paramEmpresa1, Empresa paramEmpresa2, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, Especialidad paramEspecialidad, PersonaResponsable paramPersonaResponsable, int paramInt1, FacturaCliente paramFacturaCliente, EntidadUsuario paramEntidadUsuario, int paramInt2, boolean paramBoolean);
  
  public abstract void validarBonos(FacturaCliente paramFacturaCliente)
    throws AS2Exception;
  
  public abstract void eliminarAnularBono(DetalleFacturaCliente paramDetalleFacturaCliente);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioEmisionBono
 * JD-Core Version:    0.7.0.1
 */