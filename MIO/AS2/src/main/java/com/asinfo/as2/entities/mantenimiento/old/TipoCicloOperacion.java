/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="tipo_ciclo_operacion")
/*  16:    */ public class TipoCicloOperacion
/*  17:    */   extends EntidadBase
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -7218722817948921054L;
/*  20:    */   @Id
/*  21:    */   @TableGenerator(name="tipo_ciclo_operacion", initialValue=0, allocationSize=50)
/*  22:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_ciclo_operacion")
/*  23:    */   @Column(name="id_tipo_ciclo_operacion")
/*  24:    */   private int idTipoCicloOperacion;
/*  25:    */   @Column(name="id_organizacion", nullable=false)
/*  26:    */   private int idOrganizacion;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="codigo", nullable=false, length=10)
/*  30:    */   @NotNull
/*  31:    */   @Size(max=10)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="nombre", nullable=false, length=50)
/*  34:    */   @NotNull
/*  35:    */   @Size(max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   
/*  45:    */   public TipoCicloOperacion() {}
/*  46:    */   
/*  47:    */   public TipoCicloOperacion(int idTipoCicloOperacion, String nombre)
/*  48:    */   {
/*  49: 98 */     this.idTipoCicloOperacion = idTipoCicloOperacion;
/*  50: 99 */     this.nombre = nombre;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdTipoCicloOperacion()
/*  54:    */   {
/*  55:112 */     return this.idTipoCicloOperacion;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdTipoCicloOperacion(int idTipoCicloOperacion)
/*  59:    */   {
/*  60:122 */     this.idTipoCicloOperacion = idTipoCicloOperacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdOrganizacion()
/*  64:    */   {
/*  65:131 */     return this.idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdOrganizacion(int idOrganizacion)
/*  69:    */   {
/*  70:141 */     this.idOrganizacion = idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdSucursal()
/*  74:    */   {
/*  75:150 */     return this.idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdSucursal(int idSucursal)
/*  79:    */   {
/*  80:160 */     this.idSucursal = idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getCodigo()
/*  84:    */   {
/*  85:169 */     return this.codigo;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setCodigo(String codigo)
/*  89:    */   {
/*  90:179 */     this.codigo = codigo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getNombre()
/*  94:    */   {
/*  95:188 */     return this.nombre;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setNombre(String nombre)
/*  99:    */   {
/* 100:198 */     this.nombre = nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getDescripcion()
/* 104:    */   {
/* 105:207 */     return this.descripcion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setDescripcion(String descripcion)
/* 109:    */   {
/* 110:217 */     this.descripcion = descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isActivo()
/* 114:    */   {
/* 115:226 */     return this.activo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setActivo(boolean activo)
/* 119:    */   {
/* 120:236 */     this.activo = activo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isPredeterminado()
/* 124:    */   {
/* 125:245 */     return this.predeterminado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPredeterminado(boolean predeterminado)
/* 129:    */   {
/* 130:255 */     this.predeterminado = predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getId()
/* 134:    */   {
/* 135:265 */     return this.idTipoCicloOperacion;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.TipoCicloOperacion
 * JD-Core Version:    0.7.0.1
 */