/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.OneToMany;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="lista_verificacion")
/*  20:    */ public class ListaVerificacion
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 863422232634546731L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="lista_verificacion", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="lista_verificacion")
/*  27:    */   @Column(name="id_lista_verificacion")
/*  28:    */   private int idListaVerificacion;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="codigo", nullable=false, length=10)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=10)
/*  36:    */   private String codigo;
/*  37:    */   @Column(name="nombre", nullable=false, length=50)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=2, max=50)
/*  40:    */   private String nombre;
/*  41:    */   @Column(name="descripcion", length=200)
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   @Column(name="predeterminado", nullable=false)
/*  47:    */   private boolean predeterminado;
/*  48:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="listaVerificacion")
/*  49: 77 */   private List<DetalleListaVerificacion> listaDetalleListaVerificacion = new ArrayList();
/*  50:    */   
/*  51:    */   public int getIdListaVerificacion()
/*  52:    */   {
/*  53: 90 */     return this.idListaVerificacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdListaVerificacion(int idListaVerificacion)
/*  57:    */   {
/*  58:100 */     this.idListaVerificacion = idListaVerificacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdOrganizacion()
/*  62:    */   {
/*  63:109 */     return this.idOrganizacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdOrganizacion(int idOrganizacion)
/*  67:    */   {
/*  68:119 */     this.idOrganizacion = idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdSucursal()
/*  72:    */   {
/*  73:128 */     return this.idSucursal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdSucursal(int idSucursal)
/*  77:    */   {
/*  78:138 */     this.idSucursal = idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getCodigo()
/*  82:    */   {
/*  83:147 */     return this.codigo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setCodigo(String codigo)
/*  87:    */   {
/*  88:157 */     this.codigo = codigo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getNombre()
/*  92:    */   {
/*  93:166 */     return this.nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setNombre(String nombre)
/*  97:    */   {
/*  98:176 */     this.nombre = nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getDescripcion()
/* 102:    */   {
/* 103:185 */     return this.descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setDescripcion(String descripcion)
/* 107:    */   {
/* 108:195 */     this.descripcion = descripcion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isActivo()
/* 112:    */   {
/* 113:204 */     return this.activo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setActivo(boolean activo)
/* 117:    */   {
/* 118:214 */     this.activo = activo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isPredeterminado()
/* 122:    */   {
/* 123:223 */     return this.predeterminado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPredeterminado(boolean predeterminado)
/* 127:    */   {
/* 128:233 */     this.predeterminado = predeterminado;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<DetalleListaVerificacion> getListaDetalleListaVerificacion()
/* 132:    */   {
/* 133:242 */     return this.listaDetalleListaVerificacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setListaDetalleListaVerificacion(List<DetalleListaVerificacion> listaDetalleListaVerificacion)
/* 137:    */   {
/* 138:252 */     this.listaDetalleListaVerificacion = listaDetalleListaVerificacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getId()
/* 142:    */   {
/* 143:257 */     return this.idListaVerificacion;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.ListaVerificacion
 * JD-Core Version:    0.7.0.1
 */