package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.Ciudad;
import com.asinfo.as2.entities.Contacto;
import com.asinfo.as2.entities.CuentaBancariaEmpresa;
import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.DireccionEmpresa;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.EmpresaAtributo;
import com.asinfo.as2.entities.FormaPagoSRI;
import com.asinfo.as2.entities.FormacionAcademica;
import com.asinfo.as2.entities.LlamadoAtencion;
import com.asinfo.as2.entities.ProductoUltimaCompra;
import com.asinfo.as2.entities.RecargoListaPreciosCliente;
import com.asinfo.as2.entities.Subempresa;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.TipoIdentificacion;
import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import com.asinfo.as2.entities.sri.AutorizacionProveedorSRI;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Genero;
import com.asinfo.as2.enumeraciones.ReporteEnvioMailsEnum;
import com.asinfo.as2.enumeraciones.TipoEmpresa;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEmpresa
{
  public abstract void guardar(Empresa paramEmpresa)
    throws AS2Exception, ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract void eliminar(Empresa paramEmpresa)
    throws ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract Empresa buscarPorId(Integer paramInteger);
  
  public abstract Empresa cargarDetalle(Empresa paramEmpresa);
  
  public abstract Empresa cargarDetalleTodo(Empresa paramEmpresa);
  
  public abstract List<Empresa> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Empresa> obtenerClientes(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Empresa> obtenerClientes();
  
  public abstract List<Empresa> obtenerProveedores(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Empresa> obtenerProveedores();
  
  public abstract List<Empresa> obtenerEmpleados();
  
  public abstract List<Empresa> autocompletarClientes(String paramString);
  
  public abstract List<Empresa> autocompletarClientesTransportista(String paramString, HashMap<String, String> paramHashMap);
  
  public abstract List<Subempresa> autocompletarSubEmpresa(String paramString, Empresa paramEmpresa);
  
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
  
  public abstract List<AutorizacionProveedorSRI> obtenerListaComboAutorizacionSRI(int paramInt, String paramString, Date paramDate);
  
  public abstract Empresa buscarEmpresaPorIdentificacion(String paramString)
    throws ExcepcionAS2;
  
  public abstract Empresa obtenerDatosProveedor(int paramInt);
  
  public abstract Empresa bucarEmpresaPorIdentificacion(Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract DireccionEmpresa obtieneDireccionPrincipal(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<Empresa> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Empresa crearClienteConDatosBasicos(String paramString1, TipoIdentificacion paramTipoIdentificacion, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, Ciudad paramCiudad, EntidadUsuario paramEntidadUsuario, String paramString5)
    throws ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract List<Empresa> obtenerEmpresas(int paramInt);
  
  public abstract Empresa obtenerDatosCliente(int paramInt);
  
  public abstract List<RecargoListaPreciosCliente> obtenerRecargos(Empresa paramEmpresa);
  
  public abstract Empresa crearClienteConDatosBasicos(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, String paramString6)
    throws ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract AutorizacionProveedorSRI buscarAutorizacion(int paramInt1, String paramString, Date paramDate, int paramInt2)
    throws ExcepcionAS2Compras;
  
  public abstract List<Subempresa> obtenerListaComboSubEmpresa(int paramInt);
  
  public abstract List<Subempresa> obtenerListaComboSubEmpresa(int paramInt, boolean paramBoolean);
  
  public abstract List<FormaPagoSRI> getListaFormaPagoSRIEmpresa(String paramString, int paramInt);
  
  public abstract Empresa buscarEmpresaPorIdentificacion(int paramInt, String paramString)
    throws ExcepcionAS2;
  
  public abstract Boolean empresaExiste(int paramInt, String paramString)
    throws ExcepcionAS2;
  
  public abstract List getFichaEmpleado(int paramInt);
  
  public abstract List<FormacionAcademica> getFichaEmpleadoFormacionAcademica(int paramInt);
  
  public abstract void actualizarActivoSalidaEmpleado(Empresa paramEmpresa, int paramInt, boolean paramBoolean);
  
  public abstract void actualizarDatos(Empresa paramEmpresa);
  
  public abstract List<Object[]> listaEmpledos(Date paramDate1, Date paramDate2, Departamento paramDepartamento, Sucursal paramSucursal, Genero paramGenero, int paramInt1, String paramString1, String paramString2, int paramInt2);
  
  public abstract List<Object[]> reporteEmpresas(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2, TipoEmpresa paramTipoEmpresa, Empresa paramEmpresa);
  
  public abstract void actualizarMails(Empresa paramEmpresa, String paramString, DocumentoBase paramDocumentoBase);
  
  public abstract Empresa crearEmpresaConDatosBasicos(int paramInt1, String paramString1, String paramString2, TipoIdentificacion paramTipoIdentificacion, String paramString3, String paramString4, String paramString5, int paramInt2, int paramInt3, Ciudad paramCiudad, EntidadUsuario paramEntidadUsuario, String paramString6, boolean paramBoolean1, boolean paramBoolean2, TipoEmpresa paramTipoEmpresa, List<EmpresaAtributo> paramList)
    throws ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract List<Contacto> obtenerListaComboContactos(Empresa paramEmpresa);
  
  public abstract Subempresa buscarPorIdSubempresa(Integer paramInteger);
  
  public abstract String cargarMails(Empresa paramEmpresa, DocumentoBase paramDocumentoBase);
  
  public abstract void guardarActualizacionCliente(Empresa paramEmpresa)
    throws ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract void actualizarCreditoMaximo(Empresa paramEmpresa);
  
  public abstract List<Empresa> autocompletarProveedores(String paramString, boolean paramBoolean);
  
  public abstract void flush();
  
  public abstract List<CuentaBancariaEmpresa> obtenerListaCuentaBancariaEmpresa(int paramInt1, int paramInt2);
  
  public abstract List<Empresa> autocompletarClientes(String paramString, boolean paramBoolean1, CategoriaEmpresa paramCategoriaEmpresa, boolean paramBoolean2);
  
  public abstract List<Empresa> autocompletarClientes(String paramString, boolean paramBoolean);
  
  public abstract List<Empresa> autocompletarProveedores(String paramString, boolean paramBoolean, CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract Empresa cargarDetalleAtributos(Empresa paramEmpresa);
  
  public abstract Empresa cargarDetalle(Empresa paramEmpresa, boolean paramBoolean);
  
  public abstract boolean verificarCodigoEmpresa(Empresa paramEmpresa);
  
  public abstract List<Empresa> autocompletarClientes(String paramString, Map<String, String> paramMap);
  
  public abstract void validarIdentificacion(String paramString)
    throws ExcepcionAS2Identification;
  
  public abstract Empresa cargarDetalleProducto(Empresa paramEmpresa);
  
  public abstract void guardarProductoEmpresa(Empresa paramEmpresa);
  
  public abstract List<ProductoUltimaCompra> obtenerProveedoresProductoEspecifico(int paramInt);
  
  public abstract List<Object[]> listaCuentaBancariaEmpresa(int paramInt);
  
  public abstract Empresa crearClienteProveedorConDatosBasicos(String paramString1, TipoIdentificacion paramTipoIdentificacion, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, Ciudad paramCiudad, EntidadUsuario paramEntidadUsuario, String paramString5, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Identification, Exception;
  
  public abstract List<Empresa> autocompletarClientes(String paramString, boolean paramBoolean, CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract List<LlamadoAtencion> cargarListaLlamadosAtencion(int paramInt);
  
  public abstract String cargarMails(Empresa paramEmpresa, DocumentoBase paramDocumentoBase, ReporteEnvioMailsEnum paramReporteEnvioMailsEnum);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioEmpresa
 * JD-Core Version:    0.7.0.1
 */