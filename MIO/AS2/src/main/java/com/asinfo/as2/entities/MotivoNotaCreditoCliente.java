/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ import org.hibernate.annotations.ColumnDefault;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="motivo_nota_credito_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class MotivoNotaCreditoCliente
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -6592981785418453353L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="motivo_nota_credito_cliente", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="motivo_nota_credito_cliente")
/*  23:    */   @Column(name="id_motivo_nota_credito_cliente")
/*  24:    */   private int idMotivoNotaCreditoCliente;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="codigo", nullable=false, length=10)
/*  30:    */   @NotNull
/*  31:    */   @Size(min=2, max=10)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="nombre", nullable=false, length=50)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @Column(name="indicador_afecta_devolucion", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private boolean indicadorAfectaDevolucion;
/*  47:    */   @Column(name="indicador_reversa_transformacion", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   @ColumnDefault("'0'")
/*  50:    */   private boolean indicadorReversaTransformacion;
/*  51:    */   
/*  52:    */   public int getId()
/*  53:    */   {
/*  54: 99 */     return this.idMotivoNotaCreditoCliente;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdMotivoNotaCreditoCliente()
/*  58:    */   {
/*  59:106 */     return this.idMotivoNotaCreditoCliente;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdMotivoNotaCreditoCliente(int idMotivoNotaCreditoCliente)
/*  63:    */   {
/*  64:114 */     this.idMotivoNotaCreditoCliente = idMotivoNotaCreditoCliente;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdOrganizacion()
/*  68:    */   {
/*  69:121 */     return this.idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdOrganizacion(int idOrganizacion)
/*  73:    */   {
/*  74:129 */     this.idOrganizacion = idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdSucursal()
/*  78:    */   {
/*  79:136 */     return this.idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdSucursal(int idSucursal)
/*  83:    */   {
/*  84:144 */     this.idSucursal = idSucursal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getCodigo()
/*  88:    */   {
/*  89:151 */     return this.codigo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setCodigo(String codigo)
/*  93:    */   {
/*  94:159 */     this.codigo = codigo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getNombre()
/*  98:    */   {
/*  99:166 */     return this.nombre;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setNombre(String nombre)
/* 103:    */   {
/* 104:174 */     this.nombre = nombre;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getDescripcion()
/* 108:    */   {
/* 109:181 */     return this.descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setDescripcion(String descripcion)
/* 113:    */   {
/* 114:189 */     this.descripcion = descripcion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isActivo()
/* 118:    */   {
/* 119:196 */     return this.activo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setActivo(boolean activo)
/* 123:    */   {
/* 124:204 */     this.activo = activo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isPredeterminado()
/* 128:    */   {
/* 129:211 */     return this.predeterminado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setPredeterminado(boolean predeterminado)
/* 133:    */   {
/* 134:219 */     this.predeterminado = predeterminado;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isIndicadorAfectaDevolucion()
/* 138:    */   {
/* 139:223 */     return this.indicadorAfectaDevolucion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setIndicadorAfectaDevolucion(boolean indicadorAfectaDevolucion)
/* 143:    */   {
/* 144:227 */     this.indicadorAfectaDevolucion = indicadorAfectaDevolucion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isIndicadorReversaTransformacion()
/* 148:    */   {
/* 149:231 */     return this.indicadorReversaTransformacion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setIndicadorReversaTransformacion(boolean indicadorReversaTransformacion)
/* 153:    */   {
/* 154:235 */     this.indicadorReversaTransformacion = indicadorReversaTransformacion;
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MotivoNotaCreditoCliente
 * JD-Core Version:    0.7.0.1
 */