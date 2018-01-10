/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="version_comision", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_tarjeta_credito", "codigo"})})
/*  25:    */ public class VersionComision
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="version_comision", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="version_comision")
/*  32:    */   @Column(name="id_version_comision")
/*  33:    */   private int idVersionComision;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="codigo", length=10, nullable=false)
/*  39:    */   @Size(min=2, max=10)
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="nombre", length=50, nullable=false)
/*  42:    */   @Size(min=2, max=50)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @Temporal(TemporalType.DATE)
/*  52:    */   @Column(name="fecha_desde", nullable=true, length=23)
/*  53:    */   @NotNull
/*  54:    */   private Date fechaDesde;
/*  55:    */   @Temporal(TemporalType.DATE)
/*  56:    */   @Column(name="fecha_hasta", nullable=true, length=23)
/*  57:    */   private Date fechaHasta;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_tarjeta_credito", nullable=true)
/*  60:    */   private TarjetaCredito tarjetaCredito;
/*  61:    */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="versionComision")
/*  62: 92 */   private List<Comision> listaComision = new ArrayList();
/*  63:    */   
/*  64:    */   public int getId()
/*  65:    */   {
/*  66: 97 */     return this.idVersionComision;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdVersionComision()
/*  70:    */   {
/*  71:101 */     return this.idVersionComision;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdVersionComision(int idVersionComision)
/*  75:    */   {
/*  76:105 */     this.idVersionComision = idVersionComision;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdOrganizacion()
/*  80:    */   {
/*  81:109 */     return this.idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdOrganizacion(int idOrganizacion)
/*  85:    */   {
/*  86:113 */     this.idOrganizacion = idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdSucursal()
/*  90:    */   {
/*  91:117 */     return this.idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdSucursal(int idSucursal)
/*  95:    */   {
/*  96:121 */     this.idSucursal = idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getDescripcion()
/* 100:    */   {
/* 101:125 */     return this.descripcion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setDescripcion(String descripcion)
/* 105:    */   {
/* 106:129 */     this.descripcion = descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isActivo()
/* 110:    */   {
/* 111:133 */     return this.activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setActivo(boolean activo)
/* 115:    */   {
/* 116:137 */     this.activo = activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isPredeterminado()
/* 120:    */   {
/* 121:141 */     return this.predeterminado;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setPredeterminado(boolean predeterminado)
/* 125:    */   {
/* 126:145 */     this.predeterminado = predeterminado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Date getFechaDesde()
/* 130:    */   {
/* 131:149 */     return this.fechaDesde;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setFechaDesde(Date fechaDesde)
/* 135:    */   {
/* 136:153 */     this.fechaDesde = fechaDesde;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Date getFechaHasta()
/* 140:    */   {
/* 141:157 */     return this.fechaHasta;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setFechaHasta(Date fechaHasta)
/* 145:    */   {
/* 146:161 */     this.fechaHasta = fechaHasta;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setTarjetaCredito(TarjetaCredito tarjetaCredito)
/* 150:    */   {
/* 151:165 */     this.tarjetaCredito = tarjetaCredito;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String getCodigo()
/* 155:    */   {
/* 156:169 */     return this.codigo;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setCodigo(String codigo)
/* 160:    */   {
/* 161:173 */     this.codigo = codigo;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String getNombre()
/* 165:    */   {
/* 166:177 */     return this.nombre;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setNombre(String nombre)
/* 170:    */   {
/* 171:181 */     this.nombre = nombre;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public List<Comision> getListaComision()
/* 175:    */   {
/* 176:186 */     return this.listaComision;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setListaComision(List<Comision> listaComision)
/* 180:    */   {
/* 181:190 */     this.listaComision = listaComision;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public TarjetaCredito getTarjetaCredito()
/* 185:    */   {
/* 186:194 */     return this.tarjetaCredito;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String toString()
/* 190:    */   {
/* 191:199 */     if (this.fechaHasta == null) {
/* 192:200 */       return this.nombre + " valido desde: " + FuncionesUtiles.dateToString(this.fechaDesde) + " hasta : Indefinido";
/* 193:    */     }
/* 194:202 */     return this.nombre + " valido desde: " + FuncionesUtiles.dateToString(this.fechaDesde) + " hasta : " + FuncionesUtiles.dateToString(this.fechaHasta);
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.VersionComision
 * JD-Core Version:    0.7.0.1
 */