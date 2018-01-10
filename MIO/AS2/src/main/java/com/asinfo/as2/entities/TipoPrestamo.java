/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="tipo_prestamo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class TipoPrestamo
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = -6382637469289540396L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="tipo_prestamo", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_prestamo")
/*  25:    */   @Column(name="id_tipo_prestamo")
/*  26:    */   private int idTipoPrestamo;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", length=10, nullable=false)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=2, max=10)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", nullable=false, length=50)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", nullable=false)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="indicador_contabilizar", nullable=true)
/*  43:    */   private boolean indicadorContabilizar;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   @Column(name="predeterminado", nullable=false)
/*  47:    */   private boolean predeterminado;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_rubro", nullable=true)
/*  50:    */   private Rubro rubro;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_cuenta_contable", nullable=true)
/*  53:    */   private CuentaContable cuentaContable;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_documento", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private Documento documento;
/*  58:    */   
/*  59:    */   public TipoPrestamo() {}
/*  60:    */   
/*  61:    */   public TipoPrestamo(int idTipoPrestamo, String codigo, String nombre)
/*  62:    */   {
/*  63:109 */     this.idTipoPrestamo = idTipoPrestamo;
/*  64:110 */     this.nombre = nombre;
/*  65:111 */     this.codigo = codigo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getId()
/*  69:    */   {
/*  70:125 */     return this.idTipoPrestamo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdTipoPrestamo()
/*  74:    */   {
/*  75:134 */     return this.idTipoPrestamo;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdTipoPrestamo(int idTipoPrestamo)
/*  79:    */   {
/*  80:144 */     this.idTipoPrestamo = idTipoPrestamo;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdOrganizacion()
/*  84:    */   {
/*  85:153 */     return this.idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdOrganizacion(int idOrganizacion)
/*  89:    */   {
/*  90:163 */     this.idOrganizacion = idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdSucursal()
/*  94:    */   {
/*  95:172 */     return this.idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdSucursal(int idSucursal)
/*  99:    */   {
/* 100:182 */     this.idSucursal = idSucursal;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getNombre()
/* 104:    */   {
/* 105:191 */     return this.nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setNombre(String nombre)
/* 109:    */   {
/* 110:201 */     this.nombre = nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getCodigo()
/* 114:    */   {
/* 115:210 */     return this.codigo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setCodigo(String codigo)
/* 119:    */   {
/* 120:220 */     this.codigo = codigo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getDescripcion()
/* 124:    */   {
/* 125:229 */     return this.descripcion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setDescripcion(String descripcion)
/* 129:    */   {
/* 130:239 */     this.descripcion = descripcion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isActivo()
/* 134:    */   {
/* 135:248 */     return this.activo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setActivo(boolean activo)
/* 139:    */   {
/* 140:258 */     this.activo = activo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isPredeterminado()
/* 144:    */   {
/* 145:267 */     return this.predeterminado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setPredeterminado(boolean predeterminado)
/* 149:    */   {
/* 150:277 */     this.predeterminado = predeterminado;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Rubro getRubro()
/* 154:    */   {
/* 155:286 */     return this.rubro;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setRubro(Rubro rubro)
/* 159:    */   {
/* 160:296 */     this.rubro = rubro;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Documento getDocumento()
/* 164:    */   {
/* 165:305 */     return this.documento;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setDocumento(Documento documento)
/* 169:    */   {
/* 170:315 */     this.documento = documento;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public CuentaContable getCuentaContable()
/* 174:    */   {
/* 175:324 */     return this.cuentaContable;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 179:    */   {
/* 180:334 */     this.cuentaContable = cuentaContable;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean isIndicadorContabilizar()
/* 184:    */   {
/* 185:342 */     return this.indicadorContabilizar;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setIndicadorContabilizar(boolean indicadorContabilizar)
/* 189:    */   {
/* 190:350 */     this.indicadorContabilizar = indicadorContabilizar;
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoPrestamo
 * JD-Core Version:    0.7.0.1
 */