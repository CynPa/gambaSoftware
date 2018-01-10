package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.seguridad.EntidadProceso;
import com.asinfo.as2.entities.seguridad.EntidadSistema;
import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.seguridad.modelo.Usuario;
import com.asinfo.as2.util.exception.ServiceException;
import com.asinfo.as2.utils.NodoArbol;
import com.asinfo.as2.ws.seguridad.model.UsuarioWSEntity;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.security.auth.login.LoginException;

@Local
public abstract interface ServicioUsuario
{
  public abstract void guardar(EntidadUsuario paramEntidadUsuario)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void guardar(EntidadUsuario paramEntidadUsuario, boolean paramBoolean)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(EntidadUsuario paramEntidadUsuario);
  
  public abstract EntidadUsuario buscarPorId(Integer paramInteger);
  
  public abstract Usuario login(String paramString1, String paramString2)
    throws ServiceException, LoginException;
  
  public abstract void logout(Usuario paramUsuario)
    throws ServiceException;
  
  public abstract List<EntidadUsuario> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap, List<Integer> paramList);
  
  public abstract List<EntidadUsuario> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap, List<Integer> paramList);
  
  public abstract EntidadUsuario cargarDetalle(int paramInt1, int paramInt2);
  
  public abstract EntidadUsuario cargarDetalleRutaVendedor(int paramInt);
  
  public abstract List<EntidadUsuario> obtenerListaAgenteComercial(int paramInt);
  
  public abstract EntidadUsuario buscaAgenteComercialPorIdEmpresa(int paramInt);
  
  public abstract EntidadUsuario obtieneUsuarioAutorizaVentas(String paramString1, String paramString2)
    throws ExcepcionAS2;
  
  public abstract void actualizarClave(EntidadUsuario paramEntidadUsuario);
  
  public abstract List<EntidadUsuario> getEntidadUsuario(int paramInt, boolean paramBoolean, Sucursal paramSucursal);
  
  public abstract List<EntidadProceso> getProcesos(Usuario paramUsuario, EntidadSistema paramEntidadSistema, Organizacion paramOrganizacion);
  
  public abstract UsuarioWSEntity login(String paramString1, String paramString2, String paramString3)
    throws AS2Exception;
  
  public abstract List<EntidadProceso> getProcesos(UsuarioWSEntity paramUsuarioWSEntity, EntidadSistema paramEntidadSistema);
  
  public abstract void guardarNuevo(EntidadUsuario paramEntidadUsuario);
  
  public abstract List<Object[]> getListaPermisosUsuario(int paramInt);
  
  public abstract List<EntidadUsuario> getEntidadUsuario(int paramInt, boolean paramBoolean, Sucursal paramSucursal, Boolean paramBoolean1);
  
  public abstract List<EntidadUsuario> obtenerListaAgenteComercial(int paramInt, Boolean paramBoolean);
  
  public abstract List<EntidadUsuario> autocompletarUsuarios(String paramString);
  
  public abstract List<Object[]> listaRepoteRutaVendedor(EntidadUsuario paramEntidadUsuario);
  
  public abstract List<EntidadUsuario> autocompletarSuperiores(String paramString, EntidadUsuario paramEntidadUsuario);
  
  public abstract List<DocumentoBase> getListaProcesosJerarquia();
  
  public abstract NodoArbol<EntidadUsuario> obtenerJerarquiaUsuario(EntidadUsuario paramEntidadUsuario1, NodoArbol<EntidadUsuario> paramNodoArbol, EntidadUsuario paramEntidadUsuario2, boolean paramBoolean, DocumentoBase paramDocumentoBase)
    throws ExcepcionAS2;
  
  public abstract NodoArbol<EntidadUsuario> obtenerJerarquiaUsuario(EntidadUsuario paramEntidadUsuario1, NodoArbol<EntidadUsuario> paramNodoArbol, EntidadUsuario paramEntidadUsuario2, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List<EntidadUsuario> buscarJerarquiaInmediata(int paramInt, boolean paramBoolean, DocumentoBase paramDocumentoBase);
  
  public abstract List<EntidadUsuario> buscarUsuarioPorSistemaYOrganizacion(String paramString, Integer paramInteger);
  
  public abstract List<EntidadUsuario> obtenerListaAgenteComercial(int paramInt, Boolean paramBoolean1, Boolean paramBoolean2);
  
  public abstract void verificarJerarquiaUsuario(Usuario paramUsuario, DocumentoBase paramDocumentoBase)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioUsuario
 * JD-Core Version:    0.7.0.1
 */