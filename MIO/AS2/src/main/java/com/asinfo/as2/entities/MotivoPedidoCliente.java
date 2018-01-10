/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.Size;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="motivo_pedido_cliente", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  14:    */ public class MotivoPedidoCliente
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1L;
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="motivo_pedido_cliente", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="motivo_pedido_cliente")
/*  21:    */   @Column(name="id_motivo_pedido_cliente")
/*  22:    */   private int idMotivoPedidoCliente;
/*  23:    */   @Column(name="id_organizacion", nullable=false)
/*  24:    */   private int idOrganizacion;
/*  25:    */   @Column(name="id_sucursal", nullable=false)
/*  26:    */   private int idSucursal;
/*  27:    */   @Column(name="nombre", nullable=false, length=50)
/*  28:    */   @Size(min=2, max=50)
/*  29:    */   private String nombre;
/*  30:    */   @Size(min=2, max=10)
/*  31:    */   @Column(name="codigo", nullable=false)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="descripcion", nullable=true)
/*  34:    */   @Size(max=200)
/*  35:    */   private String descripcion;
/*  36:    */   @Column(name="activo", nullable=false)
/*  37:    */   private boolean activo;
/*  38:    */   @Column(name="predeterminado", nullable=false)
/*  39:    */   private boolean predeterminado;
/*  40:    */   
/*  41:    */   public int getIdMotivoPedidoCliente()
/*  42:    */   {
/*  43: 89 */     return this.idMotivoPedidoCliente;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setIdMotivoPedidoCliente(int idMotivoPedidoCliente)
/*  47:    */   {
/*  48: 99 */     this.idMotivoPedidoCliente = idMotivoPedidoCliente;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdOrganizacion()
/*  52:    */   {
/*  53:108 */     return this.idOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdOrganizacion(int idOrganizacion)
/*  57:    */   {
/*  58:118 */     this.idOrganizacion = idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdSucursal()
/*  62:    */   {
/*  63:127 */     return this.idSucursal;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdSucursal(int idSucursal)
/*  67:    */   {
/*  68:137 */     this.idSucursal = idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getNombre()
/*  72:    */   {
/*  73:146 */     return this.nombre;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setNombre(String nombre)
/*  77:    */   {
/*  78:156 */     this.nombre = nombre;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getCodigo()
/*  82:    */   {
/*  83:165 */     return this.codigo;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setCodigo(String codigo)
/*  87:    */   {
/*  88:175 */     this.codigo = codigo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getDescripcion()
/*  92:    */   {
/*  93:184 */     return this.descripcion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setDescripcion(String descripcion)
/*  97:    */   {
/*  98:194 */     this.descripcion = descripcion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isActivo()
/* 102:    */   {
/* 103:203 */     return this.activo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setActivo(boolean activo)
/* 107:    */   {
/* 108:213 */     this.activo = activo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isPredeterminado()
/* 112:    */   {
/* 113:222 */     return this.predeterminado;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setPredeterminado(boolean predeterminado)
/* 117:    */   {
/* 118:232 */     this.predeterminado = predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getId()
/* 122:    */   {
/* 123:242 */     return this.idMotivoPedidoCliente;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.MotivoPedidoCliente
 * JD-Core Version:    0.7.0.1
 */