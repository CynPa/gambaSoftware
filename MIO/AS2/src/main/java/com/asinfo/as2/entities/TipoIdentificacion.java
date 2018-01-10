/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
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
/*  19:    */ @Table(name="tipo_identificacion")
/*  20:    */ public class TipoIdentificacion
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="tipo_identificacion", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_identificacion")
/*  28:    */   @Column(name="id_tipo_identificacion")
/*  29:    */   private int idTipoIdentificacion;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="codigo", nullable=false, length=10)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=1, max=10)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", nullable=false, length=50)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=50)
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="descripcion", length=200)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="indicador_validar_identificacion", nullable=false)
/*  46:    */   private boolean indicadorValidarIdentificacion;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @Column(name="longitud_maxima", nullable=false)
/*  52:    */   private int longitudMaxima;
/*  53:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoIdentificacion")
/*  54: 68 */   private List<TipoIdentificacionComprobanteSRI> listaTipoIdentificacionComprobanteSRI = new ArrayList();
/*  55:    */   
/*  56:    */   public TipoIdentificacion() {}
/*  57:    */   
/*  58:    */   public TipoIdentificacion(int idTipoIdentificacion, String codigo, String nombre)
/*  59:    */   {
/*  60: 80 */     this.idTipoIdentificacion = idTipoIdentificacion;
/*  61: 81 */     this.codigo = codigo;
/*  62: 82 */     this.nombre = nombre;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getId()
/*  66:    */   {
/*  67: 87 */     return this.idTipoIdentificacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdTipoIdentificacion()
/*  71:    */   {
/*  72: 96 */     return this.idTipoIdentificacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdTipoIdentificacion(int idTipoIdentificacion)
/*  76:    */   {
/*  77:106 */     this.idTipoIdentificacion = idTipoIdentificacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82:115 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87:125 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdSucursal()
/*  91:    */   {
/*  92:134 */     return this.idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdSucursal(int idSucursal)
/*  96:    */   {
/*  97:144 */     this.idSucursal = idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getCodigo()
/* 101:    */   {
/* 102:153 */     return this.codigo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setCodigo(String codigo)
/* 106:    */   {
/* 107:163 */     this.codigo = codigo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getNombre()
/* 111:    */   {
/* 112:172 */     return this.nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setNombre(String nombre)
/* 116:    */   {
/* 117:182 */     this.nombre = nombre;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getDescripcion()
/* 121:    */   {
/* 122:191 */     return this.descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDescripcion(String descripcion)
/* 126:    */   {
/* 127:201 */     this.descripcion = descripcion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isActivo()
/* 131:    */   {
/* 132:210 */     return this.activo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setActivo(boolean activo)
/* 136:    */   {
/* 137:220 */     this.activo = activo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isPredeterminado()
/* 141:    */   {
/* 142:229 */     return this.predeterminado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setPredeterminado(boolean predeterminado)
/* 146:    */   {
/* 147:239 */     this.predeterminado = predeterminado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isIndicadorValidarIdentificacion()
/* 151:    */   {
/* 152:248 */     return this.indicadorValidarIdentificacion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setIndicadorValidarIdentificacion(boolean indicadorValidarIdentificacion)
/* 156:    */   {
/* 157:258 */     this.indicadorValidarIdentificacion = indicadorValidarIdentificacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String toString()
/* 161:    */   {
/* 162:268 */     return this.nombre;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getLongitudMaxima()
/* 166:    */   {
/* 167:272 */     return this.longitudMaxima;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setLongitudMaxima(int longitudMaxima)
/* 171:    */   {
/* 172:276 */     this.longitudMaxima = longitudMaxima;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getMascara()
/* 176:    */   {
/* 177:280 */     String mascara = "";
/* 178:281 */     for (int j = 0; j < this.longitudMaxima; j++) {
/* 179:282 */       mascara = mascara + "9";
/* 180:    */     }
/* 181:285 */     return mascara;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<TipoIdentificacionComprobanteSRI> getListaTipoIdentificacionComprobanteSRI()
/* 185:    */   {
/* 186:292 */     return this.listaTipoIdentificacionComprobanteSRI;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setListaTipoIdentificacionComprobanteSRI(List<TipoIdentificacionComprobanteSRI> listaTipoIdentificacionComprobanteSRI)
/* 190:    */   {
/* 191:299 */     this.listaTipoIdentificacionComprobanteSRI = listaTipoIdentificacionComprobanteSRI;
/* 192:    */   }
/* 193:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoIdentificacion
 * JD-Core Version:    0.7.0.1
 */