package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.DireccionEmpresa;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioEmpresaRemote
{
  public abstract void guardar(Empresa paramEmpresa)
    throws ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract void eliminar(Empresa paramEmpresa)
    throws ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract Empresa buscarPorId(Integer paramInteger);
  
  public abstract Empresa cargarDetalle(Empresa paramEmpresa);
  
  public abstract List<Empresa> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Empresa> obtenerClientes(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Empresa> obtenerClientes();
  
  public abstract List<Empresa> obtenerProveedores(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Empresa> obtenerProveedores();
  
  public abstract List<Empresa> obtenerEmpleados();
  
  public abstract List<Empresa> autocompletarClientes(String paramString);
  
  public abstract List<Empresa> autocompletarProveedores(String paramString);
  
  public abstract List<Empresa> autocompletarEmpleados(String paramString);
  
  public abstract List<DireccionEmpresa> obtenerListaComboDirecciones(int paramInt);
  
  public abstract Long verificaClienteListaPrecios(int paramInt);
  
  public abstract boolean verificaExcentaImpuestos(int paramInt);
  
  public abstract List<Empresa> obtenerEmpleados(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Empresa> autocompletarAgenteComercial(String paramString);
  
  public abstract List<Empresa> obtenerListaComboAgentesComerciales();
  
  public abstract Empleado buscarEmpleadoPorId(Integer paramInteger);
  
  public abstract List<Empleado> listaAgenteComercialCombo();
  
  public abstract DireccionEmpresa buscarDireccionEmpresaPorId(int paramInt);
  
  public abstract List<AutorizacionProveedorSRI> listaAutorizacionProveedorSRI(int paramInt, Date paramDate);
  
  public abstract AutorizacionProveedorSRI buscarAutorizacionProveedorSRIPorId(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<AutorizacionProveedorSRI> obtenerListaComboAutorizacionSRI(int paramInt, String paramString);
  
  public abstract Empresa buscarEmpresaPorIdentificacion(String paramString)
    throws ExcepcionAS2;
  
  public abstract Empresa obtenerDatosProveedor(int paramInt);
  
  public abstract Empresa bucarEmpresaPorIdentificacion(Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract DireccionEmpresa obtieneDireccionPrincipal(int paramInt)
    throws ExcepcionAS2;
  
  public abstract Empresa obtenerDatosCliente(int paramInt);
  
  public abstract Empresa buscarEmpresaPorIdentificacion(String paramString, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<Empresa> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioEmpresaRemote
 * JD-Core Version:    0.7.0.1
 */